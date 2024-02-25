package com.myshop.site;

import com.myshop.common.entity.Address;
import com.myshop.common.entity.Customer;
import com.myshop.site.address.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AddressRepositoryTests {

    @Autowired private TestEntityManager entityManager;

    @Autowired private AddressRepository addressRepo;


    @Test
    public void testCreateNew() {
        Customer customer = entityManager.find(Customer.class,1);
        Address address = new Address();
        address.setFirstName("Kate");
        address.setLastName("Jojs");
        address.setCustomer(customer);
        address.setDefaultForShipping(true);
        address.setPhoneNumber("09982323");
        address.setAddress("California, United state");
        Address savedAddress = addressRepo.save(address);
        Assertions.assertThat(savedAddress.getId()).isGreaterThan(0);

    }

    @Test
    public void testFindByCustomerId() {
        int customerId = 1;
        List<Address> addresses = addressRepo.findByCustomerId(customerId);
        addresses.forEach(System.out::println);
        Assertions.assertThat(addresses.size()).isGreaterThan(0);
    }

    @Test
    public void testFindByCustomerIdAndAddressId() {
        int customerId = 1;
        Address address = addressRepo.findByCustomerIdAndId(customerId, 1);
        System.out.println(address);
        Assertions.assertThat(address.getId()).isGreaterThan(0);
    }



}
