package com.enfiny.tokens.tokengenerator.service;

import com.enfiny.tokens.tokengenerator.enums.Status;
import com.enfiny.tokens.tokengenerator.exception.NotFoundException;
import com.enfiny.tokens.tokengenerator.exception.UnAuthorizedException;
import com.enfiny.tokens.tokengenerator.filter.TokenGeneratorFilter;
import com.enfiny.tokens.tokengenerator.model.App;
import com.enfiny.tokens.tokengenerator.model.Client;
import com.enfiny.tokens.tokengenerator.model.User;
import com.enfiny.tokens.tokengenerator.repository.AppRepository;
import com.enfiny.tokens.tokengenerator.repository.ClientRepository;
import com.enfiny.tokens.tokengenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private TokenGeneratorFilter tokenGeneratorFilter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Client client = returnIfClientExists();
       // App app = getAppByClient(tokenGeneratorFilter.getAppId(),client);
//        App app = appRepository.getOne(tokenGeneratorFilter.getAppId());
//        if(app==null)
//            throw new NotFoundException("AppId is required");
        User user = userRepository.findByUsernameAndStatus(username,Status.ACTIVE);
        if (user == null)
            throw new BadCredentialsException("Bad credentials");
        System.out.println("--->>>Inside the User");
        //user.getGrantAccess().getAuthority().forEach(authority -> authority.getAuthority());
        System.out.println("--->>>>>>>>Outside the user");
       // new AccountStatusUserDetailsChecker().check(user);
        return user;
    }

    private App getAppByClient(Long appId, Client client) {
        App app = getAppById(appId);
        if(!app.getClient().getId().equals(client.getId()))
            throw new UnAuthorizedException("You are not authorized to create user.");
        return  app;
    }

    private App getAppById(Long appId) {
        App app = appRepository.getOne(appId);
        if(app == null)
            throw new NotFoundException("No app found.");
        return  app;
    }

    private Client returnIfClientExists() {
        Client client = clientRepository.getOne(getClientId());
        if (client == null)
            throw new NotFoundException("No Client found.");
        return client;
    }
    private Long getClientId(){
        Long clientId = tokenGeneratorFilter.getClientId();
        if(clientId==null)
            throw new NotFoundException("Please provide the clientId.");
        return clientId;
    }
}
