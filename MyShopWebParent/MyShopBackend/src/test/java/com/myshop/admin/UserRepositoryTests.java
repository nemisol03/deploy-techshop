package com.myshop.admin;

import com.myshop.admin.user.UserRepository;
import com.myshop.common.entity.Role;
import com.myshop.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired private UserRepository userRepository;

    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateNewUser() {
        Role editor = entityManager.find(Role.class,3);
        User user = new User("Vu","Nam","editor@gmail.com","photo.jpg",true);
        user.getRoles().add(editor);
        User savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);


            }
}
