package com.myshop.site.customer;

import com.myshop.common.entity.AuthenticationType;
import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer findByEmail(String email ) {
        return customerRepo.findByEmail(email);
    }

    public List<Customer> listAll() {
        return (List<Customer>) customerRepo.findAll();
    }

    public boolean checkUnique(Integer id,String email) {
        Customer customer = customerRepo.findByEmail(email);
        if(customer==null) return true;

        if(id==null) {
            return false;
        }else {
            if(customer.getId()!=id) {
                return false;
            }
        }


        return true;
    }
    public Customer save(Customer customer) {
        customer.setEnabled(false);
        encodePassword(customer);

        String randomVerificationCode = RandomString.make(64);

        customer.setVerificationCode(randomVerificationCode);
        customer.setAuthType(AuthenticationType.DATABASE);



        return customerRepo.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
    public boolean enable(String verificationCode) {
        Customer customer = customerRepo.findByVerificationCode(verificationCode);
        if(customer == null || customer.isEnabled()) {
            return false;
        }else {

            customerRepo.updateEnabled(customer.getId());
            return true;
        }
    }

    public Customer saveAccount(Customer customer) {
        Customer oldUser = customerRepo.findById(customer.getId()).get();
        if(customer.getPassword().isEmpty()) {
            customer.setPassword(oldUser.getPassword());
        }else {
            encodePassword(customer);
        }


        customer.setEnabled(true);
        customer.setCreatedAt(oldUser.getCreatedAt());
        customer.setResetPasswordToken(oldUser.getResetPasswordToken());
        customer.setAuthType(oldUser.getAuthType());
        return customerRepo.save(customer);
    }


    public void updateAuthType(Customer customer, AuthenticationType authType) {
        if(!customer.getAuthType().equals(authType)) {
            customerRepo.updateAuthenticationType(customer.getId(),authType);
        }
    }

    public Customer getByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    public void addCustomerUponOAuthLogin(String name,String email,AuthenticationType authType) {
        Customer customer = new Customer();
        if(email==null) {
            customer.setEmail("");
        }else {
            customer.setEmail(email);
        }
        customer.setEnabled(true);
        customer.setCreatedAt(LocalDateTime.now());
        setName(customer,name);
        customer.setAuthType(authType);
        customer.setPassword("");
        customer.setAddress("");
        customer.setPhoneNumber("");

        customerRepo.save(customer);
    }

    public void setName(Customer customer,String name) {
        String[] words = name.split(" ");
        if(words.length >1) {
            String firstName = words[0];
            String lastName = name.substring(firstName.length()).trim();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
        }else {
            customer.setFirstName(words[0]);
            customer.setLastName("");
        }
    }

    public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
        Customer customer = customerRepo.findByEmail(email);
        if(customer!=null) {
            String token = RandomString.make(30);
            customer.setResetPasswordToken(token);
            customerRepo.save(customer);
            return token;
        }else {
            throw new CustomerNotFoundException("Could not find any customer with the given email:" + email);
        }
    }

    public Customer getResetPasswordToken(String token) {

        return customerRepo.findByResetPasswordToken(token);
    }

    public void updateResetPassword(String token, String newPassword) throws CustomerNotFoundException {
        Customer customer = customerRepo.findByResetPasswordToken(token);
        if(customer==null) {
            throw new CustomerNotFoundException("Not found");
        }
        customer.setPassword(newPassword);
        encodePassword(customer);
        customer.setResetPasswordToken(null);
        customerRepo.save(customer);

    }
}
