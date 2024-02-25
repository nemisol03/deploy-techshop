package com.myshop.site.order;

import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderClientRepository extends JpaRepository<Order,Long> {

    @Query("select o from Order o where o.customer.id = ?2 and (o.firstName like ?1 or o.lastName like ?1" +
            " or o.phoneNumber like ?1 or o.address like ?1 or o.status = ?1 or o.customer.firstName = ?1 or " +
            "o.customer.lastName like ?1 ) ")
    Page<Order> search(String keyword,Integer customerId,Pageable pageable);

//    @Query("select o from Order o where o.customer.id = ?2")
//    Page<Order> findAllByCus(Pageable pageable,Integer customerId);
    Page<Order> findAllByCustomer(Pageable pageable, Customer customer);
}
