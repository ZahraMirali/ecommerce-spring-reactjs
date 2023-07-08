package com.example.demo.configuration;

import com.example.demo.security.oauth2.CustomOAuth2UserService;
import com.example.demo.security.JwtConfigurer;
import com.example.demo.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/auth/login",
                                "/api/v1/registration/**",
                                "/api/v1/perfumes/**",
                                "/api/v1/users/cart",
                                "/api/v1/order/**",
                                "/api/v1/review/**",
                                "/websocket", "/websocket/**",
                                "/img/**",
                                "/static/**",
                                "/auth/**", "/oauth2/**", "/**/*swagger*/**", "/v2/api-docs"
                                ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(
                    oauth2 -> oauth2.authorizationEndpoint(authorization -> authorization.baseUri("/oauth2/authorize"))
			        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                    .successHandler(oauthSuccessHandler)
			    )
                .apply(jwtConfigurer);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}