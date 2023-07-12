package com.example.demo.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

import static com.example.demo.constants.ErrorMessage.INVALID_JWT_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Qualifier("userDetailsServiceImpl") @Lazy
    private final UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(INVALID_JWT_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}

// @Qualifier annotation is used in Spring to resolve the ambiguity that can arise when there are multiple beans of the same type in the application context. It is used to specify which bean should be injected or autowired when there are multiple beans of the same type available.
// @Qualifier annotation can be used at the field, constructor, or method parameter level. It takes a qualifier value as an argument, which is typically a string representing the bean name or a custom qualifier annotation.
// @Qualifier public @interface CustomQualifier {} @Component @CustomQualifier public class MyBean {} @Component public class MyOtherBean { @Autowired @CustomQualifier private MyBean myBean;}

// By default, Spring initializes all singleton beans eagerly when the application context is being created.
// When you annotate a bean with @Lazy, it tells Spring to create and initialize the bean lazily, i.e., only when it is requested for the first time. This can be beneficial for improving application startup time and reducing resource usage when certain beans are not immediately needed. @Component @Lazy public class MyLazyBean {}
// By default, Spring performs eager dependency injection, meaning that all dependencies are injected at the time of bean creation. However, in some scenarios, you may want to delay the initialization of certain dependencies until they are actually needed.
// @Service public class MyService { @Lazy @Autowired private UserDetailsService userDetailsService;} In this case, The UserDetailsService will be created and injected into MyService only when MyService is explicitly requested or accessed.

// Base64.getEncoder().encodeToString(secretKey.getBytes()) encodes the bytes of the secretKey string into Base64 format and returns the resulting encoded string. the resulting Base64-encoded string will be identical on any device or platform
// "Hello".getBytes() will return an array of bytes [72, 101, 108, 108, 111], which correspond to the ASCII values of the characters in the string.
// Base64.getEncoder() returns an instance of the Base64.Encoder class, which provides functionality for encoding binary data into Base64 format.

// claims object represents the payload of the JWT. By invoking Jwts.claims(), you can obtain a new Claims object to populate with your custom claims.
// Jwts.builder() is a method that returns a new instance of the JwtBuilder class. JwtBuilder class is responsible for constructing a JSON Web Token (JWT) by setting various claims, headers, and signing the token. It provides methods to customize and define the contents of the JWT.
// signWith method is used to specify the signing algorithm and the secret key or private key to sign the JSON Web Token (JWT).
// SignatureAlgorithm.HS256 is an enum constant representing the HMAC-SHA256 signature algorithm. HMAC-SHA256 is a widely used cryptographic algorithm for generating message authentication codes (MAC) using a secret key. It uses the SHA-256 hash function in combination with a secret key to produce a secure and verifiable signature for the data.

// Jwts.parser(): This is a static method from the Jwts class that returns a JwtParser instance. The JwtParser is responsible for parsing and verifying JWTs.
// setSigningKey(secretKey): This method sets the signing key used to verify the JWT signature. The secretKey parameter represents the secret key that was used to sign the JWT.
// parseClaimsJws(token): This method parses the provided JWT token and returns a Jws<Claims> object. The token parameter represents the JWT token to be parsed. The Jws<Claims> object contains the parsed claims of the JWT and can be used to access the payload and verify the signature.

// UsernamePasswordAuthenticationToken is an object used to represent the authenticated user within the authentication process. It encapsulates the principal (typically a UserDetails object) and the credentials (e.g., password or token) provided by the user.