package com.example.demo.security.oauth2;

import com.example.demo.domain.User;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private AuthenticationService authenticationService;
    private UserService userService;

    public CustomOAuth2UserService() {}

    @Autowired
    @Lazy
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        User user = userService.getUserInfo(oAuth2UserInfo.getEmail());

        if (user == null) {
            user = authenticationService.registerOauth2User(provider, oAuth2UserInfo);
        } else {
            user = authenticationService.updateOauth2User(user, provider, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}
