package com.myshop.site;

import com.myshop.common.entity.CartItem;
import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Product;
import com.myshop.site.shoppingcart.CartItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {

    @Autowired private CartItemRepository repo;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateCartItem() {
        Customer customer = entityManager.find(Customer.class,11);
        Product product = entityManager.find(Product.class,10);
        CartItem cartItem = new CartItem(customer,product,2);
        CartItem savedItem = repo.save(cartItem);
        Assertions.assertThat(savedItem.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreate2CartItem() {
        Customer customer = entityManager.find(Customer.class,1);
        Product product1 = entityManager.find(Product.class,3);
        CartItem cartItem1 = new CartItem(customer,product1,1);
        CartItem cartItem2 = new CartItem(customer,new Product(7),10);
        Iterable<CartItem> iterable = repo.saveAll(List.of(cartItem1, cartItem2));
        Assertions.assertThat(iterable).hasSize(2);


    }

    @Test
    public void testFindByCustomer() {
        Integer customerId = 11;
        Customer customer = entityManager.find(Customer.class,customerId);
        List<CartItem> cartItems = repo.findByCustomer(customer );
        Assertions.assertThat(cartItems).hasSize(3);
    }

    @Test
    public void testFindByCustomerAndProduct() {
        Integer customerId = 11;
        Integer productId = 2;
        Customer customer = entityManager.find(Customer.class,customerId);
        Product product = entityManager.find(Product.class,productId);
        CartItem cartItem = repo.findByCustomerAndProduct(customer, product);
        Assertions.assertThat(cartItem).isNotNull();

    }
    @Test
    public void testUpdateQuantity() {
        Integer customerId = 11;
        Integer productId = 2;
        Customer customer = entityManager.find(Customer.class,customerId);
        Product product = entityManager.find(Product.class,productId);
        int quantity = 6;

        repo.updateQuantity(customer,productId,quantity);
        CartItem cartItem = repo.findByCustomerAndProduct(customer, product);
        Assertions.assertThat(cartItem.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void testDeleteCartItem() {
        Integer id = 5;
        repo.deleteById(id);
        Optional<CartItem> optional = repo.findById(id);
        Assertions.assertThat(optional).isNotPresent();
    }
}
