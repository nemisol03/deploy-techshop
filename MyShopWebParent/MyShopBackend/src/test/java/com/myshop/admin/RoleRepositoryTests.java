package com.myshop.admin;

import com.myshop.admin.user.RoleRepository;
import com.myshop.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {


    @Autowired private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role role = new Role("admin","manage everything");
        Role savedRole = roleRepository.save(role);
        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {
        Role salesperson  = new Role("Salesperson","manage product price, customers, shipping, orders and sales report");
        Role editor = new Role("Editor","manage categories, brands, products, articles and menus");
        Role assistant = new Role("Shipper","manage questions and reviews");
        Role shipper = new Role("Assistant","manage questions and reviews");
        Iterable<Role> iterable = roleRepository.saveAll(List.of(salesperson,editor,shipper,assistant));
        iterable.forEach(System.out::println);
        Assertions.assertThat(iterable).size().isEqualTo(4);
    }

    @Test
    public void testUpdateRole() {
        Integer adminId = 1;
        Optional<Role> admin = roleRepository.findById(adminId);
        Assertions.assertThat(admin).isPresent();
        String newName = "Admin";
        admin.get().setName(newName);
        Role updatedRole = admin.get();
        Assertions.assertThat(updatedRole.getName()).isEqualTo(newName);
    }
}
