package com.enfiny.tokens.tokengenerator.config;

import com.enfiny.tokens.tokengenerator.service.ClientService;
import com.enfiny.tokens.tokengenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthenticationServer extends AuthorizationServerConfigurerAdapter {
    @Value("${check-user-scopes}")
    private Boolean checkUserScopes;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    private final AuthenticationManager authenticationManager;

    Oauth2AuthenticationServer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
            CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientService);
            requestFactory.setCheckUserScopes(true);
            return requestFactory;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray()).getKeyPair("jwt"));
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)throws Exception{
            clients.withClientDetails(clientService);
    }

    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
        return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
               // .pathMapping("/oauth/token", "/tokenGenerator/api/v1/oauth/token")// here
               // .pathMapping("/oauth/check_token", "/tokenGenerator/api/v1/oauth/check_token")// here
               // .pathMapping("/oauth/confirm_access", "/tokenGenerator/api/v1/oauth/confirm_access")// here
               // .pathMapping("/oauth/error", "/tokenGenerator/api/v1/oauth/error")// here
               // .pathMapping("/oauth/token", "/tokenGenerator/api/v1/oauth/token")// here
                .tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager).userDetailsService(userService);
        if (checkUserScopes)
            endpoints.requestFactory(requestFactory());
    }
}
