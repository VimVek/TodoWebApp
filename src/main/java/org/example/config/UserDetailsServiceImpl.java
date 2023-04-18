package org.example.config;

import org.example.entity.UserDtls;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl() {
    }
//this for finding that email available in database or not for login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDtls user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("USer does not exit");
        }
        else {
            CustomUserDtls customUserDtls = new CustomUserDtls(user);
            return customUserDtls;
        }
    }
}
