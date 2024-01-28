package com.umc.gusto.global.auth.model;

import com.umc.gusto.domain.user.entity.Social;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User delegate;
    private final OAuthAttributes oAuthAttributes;
    private final Social.SocialStatus socialStatus;
    private final UUID temporalToken;

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
