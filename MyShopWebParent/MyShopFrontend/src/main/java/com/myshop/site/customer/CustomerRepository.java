package com.myshop.site.customer;

import com.myshop.common.entity.AuthenticationType;
import com.myshop.common.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Integer> {

    Customer findByEmail(String email);

    Customer findByVerificationCode(String verificationCode);

    @Query("update Customer c set c.enabled = true, c.verificationCode = null where c.id =?1")
    @Modifying
    void updateEnabled(Integer id);

    @Query("update Customer c set c.authType = ?2 where c.id= ?1")
    @Modifying
    void updateAuthenticationType(Integer customerId, AuthenticationType type);

    Customer findByResetPasswordToken(String token);
}
