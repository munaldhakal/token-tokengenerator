package com.enfiny.tokens.tokengenerator.config;

import com.enfiny.tokens.tokengenerator.service.ClientService;
import com.enfiny.tokens.tokengenerator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Map;

public class CustomOauth2RequestFactory extends DefaultOAuth2RequestFactory {
	private static final Logger LOG = LoggerFactory.getLogger(CustomOauth2RequestFactory.class);
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserService userService;

	public CustomOauth2RequestFactory(ClientService clientService) {
		super(clientService);
	}


	@Override
	public TokenRequest createTokenRequest(Map<String, String> requestParameters,
			ClientDetails authenticatedClient) {
		if (requestParameters.get("grant_type").equals("refresh_token")) {
			OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(
					tokenStore.readRefreshToken(requestParameters.get("refresh_token")));
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getName(), null,
							userService.loadUserByUsername(authentication.getName()).getAuthorities()));
		}
		LOG.debug("Requesting the token");
		return super.createTokenRequest(requestParameters, authenticatedClient);
	}
}
