package com.myshop.admin;

import com.myshop.admin.order.OrderRepository;
import com.myshop.common.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addAnOrder() {
        Product product1 = entityManager.find(Product.class, 1);
        Product product2 = entityManager.find(Product.class, 7);
        Customer customer = entityManager.find(Customer.class, 1);

        Order order = new Order();
        order.setFirstName("Nam");
        order.setLastName("Vu");
        order.setPhoneNumber("0988872231");
        order.setAddress("72 Nguyen Trai, Thanh Xuan, Ha Noi");
        order.setTotal(343);
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setCustomer(customer);


        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setQuantity(3);
        orderDetail1.setProductCost(21);
        orderDetail1.setSubtotal(10);
        orderDetail1.setProduct(product1);
        orderDetail1.setOrder(order);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setQuantity(13);
        orderDetail2.setProductCost(234);
        orderDetail2.setSubtotal(23);
        orderDetail2.setProduct(product2);
        orderDetail2.setOrder(order);

        order.getOrderDetails().addAll(Set.of(orderDetail1,orderDetail2));

        Order savedOrder = orderRepo.save(order);
        Assertions.assertThat(savedOrder.getOrderDetails().size()).isEqualTo(2);
    }
}
