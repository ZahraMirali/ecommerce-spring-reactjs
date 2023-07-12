package com.example.demo.security;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.demo.constants.ErrorMessage.USER_NOT_FOUND;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        if (user.getActivationCode() != null) {
            throw new LockedException("Email not activated");
        }
        return UserPrincipal.create(user);
    }
}

// The optional value attribute of the @Service annotation allows you to specify a custom name for the bean.
// When you use @Service without specifying a custom name, and there is only one bean of the corresponding type in the application context, you can inject it into other components simply by type without the need for the @Qualifier annotation.

// LockedException is a specific type of exception that is used in the context of authentication and user account management. It is typically thrown when a user account is locked due to certain conditions, such as too many failed login attempts or administrative actions.