package com.myshop.admin;

import com.myshop.admin.brand.BrandRepository;
import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Category;
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
public class BrandRepositoryTests {

    @Autowired private BrandRepository brandRepository;

    @Autowired private TestEntityManager entityManager;
    @Test
    public void createNewBrand() {
        Brand brand = new Brand("samsung","samsung.png");
        brand.getCategories().add(new Category(1));
        Brand savedBrand = brandRepository.save(brand);
        Assertions.assertThat(savedBrand.getId()).isGreaterThan(1);
    }

    @Test
    public void addSomeCategoriesToBrand() {
        Brand brand = brandRepository.findById(2).get();
        Category laptop = entityManager.find(Category.class,6);
        Category tablet = entityManager.find(Category.class,7);
        brand.getCategories().add(laptop);
        brand.getCategories().add(tablet);
        brandRepository.save(brand);
    }
}
