package com.scrumoftheearth.springbootapi.security;

import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = getUserByUserName(userName);
        /* https://www.marcobehler.com/guides/spring-security#_what_are_authorities_what_are_roles */
        // Role is an authority minus the ROLE_ prefix.
        SimpleGrantedAuthority simpleGA = new SimpleGrantedAuthority("ROLE_USER");
        Collection<? extends GrantedAuthority> authoritiesSingleton = Collections.singletonList(simpleGA);
        return new SecurityUser(user.getUserName(), user.getSaltedHashedPassword(), authoritiesSingleton);
    }


    public Collection<? extends GrantedAuthority> getUserRolesByUsername(String userName) throws UsernameNotFoundException {
        // User user = getUserByUserName(userName); Currently hardcoded roles
        SimpleGrantedAuthority simpleGA = new SimpleGrantedAuthority("ROLE_USER");
        return Collections.singletonList(simpleGA);
    }

    private User getUserByUserName(String userName) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findByUserName(userName);
        if (!userResult.isPresent()) {
            throw new UsernameNotFoundException(String.format("Username %s was not found!", userName));
        }
        return userResult.get();
    }
}
