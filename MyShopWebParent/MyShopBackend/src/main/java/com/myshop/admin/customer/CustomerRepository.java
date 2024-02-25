package com.myshop.admin.customer;

import com.myshop.common.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Integer> {

    @Query("select c from Customer c where c.firstName = ?1")
    Page<Customer> search(String keyword, Pageable pageable);

    @Query("update Customer c set c.enabled = ?2 where c.id=?1 ")
    @Modifying
    void updateStatus(Integer id,boolean status);
}
