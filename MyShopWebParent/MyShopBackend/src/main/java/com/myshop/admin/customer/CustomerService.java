package com.myshop.admin.customer;

import com.myshop.common.entity.Customer;
import com.myshop.common.entity.User;
import com.myshop.common.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {
    public final static int CUSTOMERS_PER_PAGE = 2;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<Customer > listAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Page<Customer> listByPage(int pageNum,String sortField,String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending() ;
        Pageable pageable = PageRequest.of(pageNum - 1,CUSTOMERS_PER_PAGE,sort);
        if(keyword!=null) {
            return customerRepository.search(keyword,pageable);
        }
        return customerRepository.findAll(pageable);
    }

    @Transactional
    public void updateStatus(Integer id, boolean status) throws CustomerNotFoundException {
        get(id);
        customerRepository.updateStatus(id,status);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        return customerRepository.findById(id).orElseThrow(
                ()-> new CustomerNotFoundException("Couldn't find any customer with id: " + id)
        );
    }

    public void deleteCustomer(Integer id) throws CustomerNotFoundException {
        get(id);
        customerRepository.deleteById(id);

    }

    public Customer saveCustomer(Customer customer) {
            Customer customerInDB = customerRepository.findById(customer.getId()).get();
        if(customer.getPassword().isEmpty()) {
            customer.setPassword(customerInDB.getPassword());
        }else {
            encodePassword(customer);
        }
        customer.setEmail(customerInDB.getEmail());
        customer.setVerificationCode(customerInDB.getVerificationCode());
        customer.setAuthType(customerInDB.getAuthType());
        customer.setResetPasswordToken(customerInDB.getResetPasswordToken());
        return customerRepository.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
}
