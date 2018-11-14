package com.enfiny.tokens.tokengenerator.config;

import java.util.*;

import com.enfiny.tokens.tokengenerator.model.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = (User) authentication.getPrincipal();

		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		Map<Object,Object> response = new HashMap<>();
		response.put("id",user.getId());
		response.put("clientId", user.getClient().getId());
		response.put("status",user.getStatus().toString());
        response.put("email",user.getEmail());
        response.put("username",user.getUsername());
        response.put("fullName",user.getFullName());
        if(user.getApp()!=null)
        response.put("appId",user.getApp().getId());
        if(user.get_id()!=null)
            response.put("_id", user.get_id());
		info.put("user",response);
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}
}