package com.myshop.site.security;

import com.myshop.common.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomerUserDetail implements UserDetails {
    private Customer customer;
    public CustomerUserDetail(Customer customer) {
        this.customer = customer;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
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
        return customer.isEnabled();
    }

    public String getAvatar() {
        if(customer.getImage()== null) {
            return "/images/no-image.jpg";
        }
        return customer.getImagePath();
    }
    public String getFullName() {
        return customer.getFirstName() + " " + customer.getLastName();
    }

    public void setFirstName(String firstName) {
        customer.setFirstName(firstName);
    }

    public void setLastName (String lastName) {
        customer.setLastName(lastName);
    }
    public void setAvatar (String imageUrl) {
        customer.setImage(imageUrl);
    }

    public Customer getCustomer() {
        return customer;
    }

//    public String getEmail() {
//        return customer.getEmail();
//    }
}
