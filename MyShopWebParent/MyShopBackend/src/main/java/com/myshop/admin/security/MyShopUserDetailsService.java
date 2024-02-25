package com.myshop.admin.security;

import com.myshop.admin.user.UserRepository;
import com.myshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class MyShopUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = userRepository.findByEmail(email);
        if(userByEmail!=null) {
            return new MyShopUserDetails(userByEmail);
        }
        throw new UsernameNotFoundException("Couldn't find any user with email : " + email);
    }
}
