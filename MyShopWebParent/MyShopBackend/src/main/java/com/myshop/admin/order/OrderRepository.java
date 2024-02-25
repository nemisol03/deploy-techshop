package com.myshop.admin.order;

import com.myshop.common.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {

    @Query("select o from Order o where o.firstName like ?1 or o.lastName like ?1" +
            " or o.phoneNumber like ?1 or o.address like ?1 or o.status = ?1 or o.customer.firstName = ?1 or " +
            "o.customer.lastName like ?1 ")
    Page<Order> search(String keyword,Pageable pageable);
}
