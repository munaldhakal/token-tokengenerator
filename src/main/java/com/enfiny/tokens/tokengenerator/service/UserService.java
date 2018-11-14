package com.enfiny.tokens.tokengenerator.service;

import com.enfiny.tokens.tokengenerator.enums.Status;
import com.enfiny.tokens.tokengenerator.model.User;
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndStatus(username, Status.ACTIVE);
        if (user == null)
            throw new BadCredentialsException("Bad credentials");
        user.getGrantAccess().getAuthority().forEach(authority -> authority.getAuthority());
        new AccountStatusUserDetailsChecker().check(user);
        return user;
    }
}
