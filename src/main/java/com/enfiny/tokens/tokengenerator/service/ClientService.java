package com.enfiny.tokens.tokengenerator.service;

import com.enfiny.tokens.tokengenerator.enums.GrantType;
import com.enfiny.tokens.tokengenerator.enums.Status;
import com.enfiny.tokens.tokengenerator.exception.AlreadyExistsException;
import com.enfiny.tokens.tokengenerator.exception.InvalidLengthException;
import com.enfiny.tokens.tokengenerator.exception.NotFoundException;
import com.enfiny.tokens.tokengenerator.exception.UnAuthorizedException;
import com.enfiny.tokens.tokengenerator.model.*;
import com.enfiny.tokens.tokengenerator.repository.*;

import com.enfiny.tokens.tokengenerator.request.ClientCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;


@Service
public class ClientService implements ClientDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private GrantAccessRepository grantAccessRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        System.out.println("-->>>Inside Override client");
	Client client = clientRepository.findByUsernameAndStatus(clientId,Status.ACTIVE);
         if(client==null)
		System.out.println("No client found of username: "+clientId);
        System.out.println("--->>>>>>>>>Headache");
        //client.getApp().forEach(app -> app.getGrantAccess().forEach(grantAccess -> grantAccess.getAuthority().forEach(authority -> authority.getAuthority())));
        return client;
    }

    @Transactional
    public void createClient(ClientCreationDto dto) {
        Client client = clientRepository.findByUsernameAndStatus(dto.getUsername(),Status.ACTIVE);
        if(client!=null)
            throw new AlreadyExistsException("Client Already Exists");
        else
            client = new Client();
        client.setUsername(dto.getUsername());
        client.setCreatedDate(new Date());
        client.setStatus(Status.ACTIVE);
        if (dto.getEmail() != null && !dto.getEmail().equals(""))
            client.setEmail(dto.getEmail());
        checkSecretLength(dto.getSecret());
        client.setSecret("{bcrypt}"+ BCrypt.hashpw(dto.getSecret(), BCrypt.gensalt()));
        client = clientRepository.save(client);
        App appToSave = new App();
        GrantAccess grantAccessToSave = new GrantAccess();
        User userToSave = new User();
        Authority authorityToSave = new Authority();
        Authority authorityToSave1 = new Authority();
        Authority authorityToSave2 = new Authority();
        Authority authorityToSave3 = new Authority();
        Authority authorityToSave4 = new Authority();
        User user = null;
        try{
            /*Client client1 = clientRepository.getOne(1L);
            App app = appRepository.getOne(1L);
            user = userRepository.findByAppAndUsernameAndStatus(app,dto.getUsername(), Status.ACTIVE);
            if(user!=null)
                throw new AlreadyExistsException("User with username: "+dto.getUsername()+" already exists.");
            user = new User();
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setApp(app);
         //   user.setClient(client);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setUsername(dto.getUsername());
            if(dto.getEmail()!=null)
                user.setEmail(dto.getEmail());
            user.setPassword("{bcrypt}"+ BCrypt.hashpw(dto.getSecret(),BCrypt.gensalt()));
            user.setStatus(Status.ACTIVE);
            GrantAccess grantAccess = grantAccessRepository.getOne(1L);
            user.setGrantAccess(grantAccess);
            user = userRepository.save(user);*/

            appToSave.setStatus(Status.ACTIVE);
            appToSave.setAppName("APP_DEFAULT");
            appToSave.setClient(client);
            appToSave = appRepository.save(appToSave);
            grantAccessToSave = new GrantAccess();
            grantAccessToSave.setGrantType(GrantType.CLIENT);
            grantAccessToSave.setApp(appToSave);
            grantAccessToSave = grantAccessRepository.save(grantAccessToSave);
            authorityToSave.setAuthority("ACTIONS_USER");
            authorityToSave.setGrantAccess(grantAccessToSave);
            authorityRepository.save(authorityToSave);
            authorityToSave1.setAuthority("ACTIONS_APP");
            authorityToSave1.setGrantAccess(grantAccessToSave);
            authorityRepository.save(authorityToSave1);
            authorityToSave2.setAuthority("ACTIONS_GRANT");
            authorityToSave2.setGrantAccess(grantAccessToSave);
            authorityRepository.save(authorityToSave2);
            authorityToSave3.setAuthority("ACTIONS_AUTHORITY");
            authorityToSave3.setGrantAccess(grantAccessToSave);
            authorityRepository.save(authorityToSave3);
            authorityToSave4.setAuthority("ACTIONS_CLIENT");
            authorityToSave4.setGrantAccess(grantAccessToSave);
            authorityRepository.save(authorityToSave4);
            userToSave.setApp(appToSave);
            userToSave.setUsername(client.getUsername());
            userToSave.setStatus(Status.ACTIVE);
            userToSave.setUsername(client.getUsername());
            userToSave.setAccountNonLocked(true);
            userToSave.setCredentialsNonExpired(true);
            userToSave.setAccountNonExpired(true);
            userToSave.setEnabled(true);
            userToSave.setGrantAccess(grantAccessToSave);
            userToSave.setEmail(client.getEmail());
            userToSave.setPassword(client.getSecret());
           // userToSave.setClient(client);
            userRepository.save(userToSave);
        }catch(Exception e){
            e.printStackTrace();
            //userRepository.delete(user);
            clientRepository.delete(client);
            appRepository.delete(appToSave);
            grantAccessRepository.delete(grantAccessToSave);
            authorityRepository.delete(authorityToSave);
            authorityRepository.delete(authorityToSave1);
            authorityRepository.delete(authorityToSave2);
            authorityRepository.delete(authorityToSave3);
            authorityRepository.delete(authorityToSave4);
            userRepository.delete(userToSave);
        }
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
    private void checkSecretLength(String secret) {
        if (secret.length() < 8)
            throw new InvalidLengthException("Secret length must be greater than or equal to 8");
    }
}
