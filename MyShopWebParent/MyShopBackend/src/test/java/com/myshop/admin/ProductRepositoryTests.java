package com.myshop.admin;

import com.myshop.admin.product.ProductRepository;
import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
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
public class ProductRepositoryTests {

    @Autowired private ProductRepository repo;

    @Autowired private TestEntityManager entityManager;
    @Test
    public void testCreatNewProduct() {
        Brand brand = entityManager.find(Brand.class,3);
        Category category = entityManager.find(Category.class,14);
        Product product = new Product();
        product.setName("SAMSUNG Galaxy S23 Ultra Cell Phone, Factory Unlocked Android Smartphone, 256GB, 200MP Camera, Night Mode, Long Battery Life, S Pen, US Version, 2023, Phantom Black");
        product.setAlias("samsung_s23_ultra");
        product.setEnabled(true);
        product.setMainImage("samsung.jpg");
        product.setShortDescription("Brand : SAMSUNG;Model Name:Samsung Galaxy S23 Ultra");
        product.setFullDescription("CAPTURE THE NIGHT IN LOW LIGHT: Whether you’re headed to a concert or romantic night out, there’s no such thing as bad lighting with Night Mode; Galaxy S23 Ultra lets you capture epic content in any setting with stunning Nightography.Aperture - Rear: 200MP (f/1.7), 12MP (f/2.2), 10MP (f/2.4), 10MP (f/4.9), Front: 12MP (f/2.2). Flaw Detection : Yes. Wireless Powershare : Yes..Peak Brightness 1750 nits");
        product.setBrand(brand);
        product.setCategory(category);
        Product savedProduct = repo.save(product);
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testAddExtraImages() {
        Product product =  repo.findById(1).get();
        product.addExtraImage("extra-image1.jpg",product);
        product.addExtraImage("extra-image2.jpg",product);
        product.addExtraImage("extra-image3.jpg",product);
        Product savedProduct = repo.save(product);
        Assertions.assertThat(savedProduct.getImages().size()).isEqualTo(3);
    }

    @Test
    public void testAddSomeDetails() {

        Product product = entityManager.find(Product.class,1);
        product.addDetail("Brand","Dell",product);
        product.addDetail("Screen size","15.6 inches",product);
        product.addDetail("Operating system","Windows 11 Home",product);
        product.addDetail("Resolution","1080p",product);

        Product savedProduct = repo.save(product);
        Assertions.assertThat(savedProduct.getDetails().size()).isEqualTo(4);

    }
}

