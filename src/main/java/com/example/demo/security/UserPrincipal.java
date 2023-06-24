package com.example.demo.security;

import com.example.demo.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public static UserPrincipal create(User user) {
        String userRole = user.getRoles().iterator().next().toString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRole));
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

// UserDetails interface provides several methods to retrieve and manage user information. getUsername(), getPassword(), getAuthorities(): Returns a collection of GrantedAuthority objects representing the authorities/roles assigned to the user, isEnabled(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired()
// OAuth2User represents the user information obtained from an OAuth2 authentication provider. getName(), getAttributes(), getAttribute(String name), getAuthorities(): Returns a collection of GrantedAuthority objects representing the authorities/roles assigned to the user.
// Typically, during the OAuth2 authentication process, after a successful authentication, the authentication provider (e.g., Google, Facebook, GitHub) returns an access token and user attributes. The access token is used to verify the user's identity, while the user attributes are retrieved and mapped to an instance of OAuth2User. This OAuth2User instance can then be used in the application to access and utilize the user's information.
// GrantedAuthority represents an authority or role granted to an authenticated user. It typically defines the permissions or access levels that a user has within an application.
// The question mark (?) in Collection<? extends GrantedAuthority> represents a wildcard or an unknown type parameter. It is used to indicate that the type parameter can be any type that is a subtype of GrantedAuthority or GrantedAuthority itself. it allows you to work with a collection that can hold instances of any class that extends GrantedAuthority, without specifying the exact type. This provides flexibility when working with different implementations or subclasses of GrantedAuthority. However, you cannot add or modify elements in the collection because the exact type of the elements is unknown.

// SimpleGrantedAuthority represents a simple authority or privilege as a string.

// Collections.singletonList() is used to create an immutable list containing a single specified element.