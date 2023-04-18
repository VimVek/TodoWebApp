package org.example.config;

import org.example.entity.UserDtls;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
//this is for getting the details and parsing it
public class CustomUserDtls implements UserDetails {

    private UserDtls userDtls;

    public CustomUserDtls(UserDtls userDtls) {
        this.userDtls = userDtls;
    }

    public CustomUserDtls() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {   //admin or user model
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userDtls.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return userDtls.getPassword();
    }

    @Override
    public String getUsername() {
        return userDtls.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
