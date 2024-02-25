package com.myshop.site.security;

import com.myshop.common.entity.Customer;
import com.myshop.site.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null) {
        return new CustomerUserDetail(customer);
        }
            throw new UsernameNotFoundException("Couldn't find any customer with email: " + email);
    }
}
