package com.scrumoftheearth.springbootapi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {
    // Use SimpleGrantedAuthority and a Singleton Collection.
    // This Constructor is a SecurityUser with no restrictions.
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }
}
