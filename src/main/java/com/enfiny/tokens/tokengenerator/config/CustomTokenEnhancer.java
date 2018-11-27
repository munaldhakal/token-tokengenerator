package com.enfiny.tokens.tokengenerator.config;

import java.util.*;

import com.enfiny.tokens.tokengenerator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.RestTemplate;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		System.out.println("--------------------------------------------------->>>>>>>>>>>>Accessing USER");
		User user = (User) authentication.getPrincipal();
		System.out.println("---------------------------------------------------->>>>>>>>>>>>Accessed");
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		Map<Object,Object> response = new HashMap<>();
		response.put("id",user.getId());
		//response.put("clientId", user.getClient().getId());
		response.put("status",user.getStatus().toString());
        response.put("email",user.getEmail());
        response.put("username",user.getUsername());
        response.put("fullName",user.getFullName());
        if(user.getApp()!=null)
			System.out.println("---------------------------------------------------->>>>>>>>>>>>APPPPPPPPPPPPPPP");
        response.put("appId",user.getApp().getId());
		System.out.println("---------------------------------------------------->>>>>>>>>>>>Accessed");
        if(user.get_id()!=null) {
			response.put("_id", user.get_id());
			ResponseEntity responseEntity = restTemplate.getForEntity("http://192.168.1.68:8520/users/getSingleUser/" +user.get_id(),String.class);
			info.put("userData",responseEntity.getBody());
		}
		info.put("user",response);
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}
}