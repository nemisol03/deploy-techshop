package com.myshop.admin;

import com.myshop.admin.category.CategoryRepository;
import com.myshop.common.entity.Category;
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
public class CategoryRepositoryTests {

    @Autowired private CategoryRepository repo;

    @Autowired private TestEntityManager entityManager;
    @Test
    public void testCreateFirstCategory() {
        Category category = new Category("component","component2","component_img.png");
        Category savedCate = repo.save(category);
        Assertions.assertThat(savedCate.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategories() {
        Category computer = entityManager.find(Category.class,1);
        Category desktop = new Category("desktop","desktop","desktop.png");
        Category laptop = new Category("laptop","laptop","laptop.png");
        desktop.setParent(computer);
        laptop.setParent(desktop);
        Iterable<Category> savedCates = repo.saveAll(List.of(desktop,laptop));
        Assertions.assertThat(savedCates).size().isGreaterThan(1);
    }

    @Test
    public void testCreateSuperSubCategories() {
        Category desktop = entityManager.find(Category.class,4);
        Category keyboard = new Category("keyboard","keyboard","desktop.png");
        Category bluetooth = new Category("bluetooth","bluetooth","laptop.png");
        keyboard.setParent(desktop);
        bluetooth.setParent(desktop);
        Iterable<Category> savedCates = repo.saveAll(List.of(keyboard,bluetooth));
        Assertions.assertThat(savedCates).size().isGreaterThan(1);
    }
}
