package com.myshop.site;

import com.myshop.common.entity.AuthenticationType;
import com.myshop.common.entity.Customer;
import com.myshop.site.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository repo;

    @Test
    public void createNewCustomer() {
        Customer customer = new Customer("Vuhoainam1@gmail.com","myPassword123","vu hoai","nam");
        Customer savedCustomer = repo.save(customer);
        Assertions.assertThat(savedCustomer.getId()).isGreaterThan(0);

    }
    @Test
    public void updateCustomer() {
        Optional<Customer> optional = repo.findById(2);
        Assertions.assertThat(optional).isNotNull();
        Customer customer = optional.get();
        customer.setEmail("modifiedEmail@gmail.com");
        repo.save(customer);


    }


    @Test
    public void listCustomers() {
        Iterable<Customer> customers = repo.findAll();
        customers.forEach(System.out::println);
        Assertions.assertThat(customers).hasSize(2);
    }

    @Test
    public void testUpdateAuthenticationType() {
        Integer cusId = 1;
        repo.updateAuthenticationType(cusId, AuthenticationType.DATABASE);
        Customer customer = repo.findById(cusId).get();
        Assertions.assertThat(customer.getAuthType()).isEqualTo(AuthenticationType.DATABASE);
    }
}
