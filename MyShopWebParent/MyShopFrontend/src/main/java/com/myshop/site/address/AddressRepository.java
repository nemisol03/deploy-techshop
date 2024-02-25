package com.myshop.site.address;

import com.myshop.common.entity.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository  extends CrudRepository<Address,Integer> {

    List<Address> findByCustomerId(Integer id);

    Address findByCustomerIdAndId(Integer customerId,Integer addressId);

    void deleteByCustomerIdAndId(Integer customerId,Integer addressId);


    @Query("update Address a set a.defaultForShipping = true where a.id=?1")
    @Modifying
     void setDefaultAddress(Integer id);

    @Query("update Address a set a.defaultForShipping = false where a.id!=?1 and a.customer.id = ?2")
    @Modifying
    void setNonDefaultForOthers(Integer defaultAddressId,Integer customerId);


    @Query("select a from Address a where a.customer.id = ?1 and a.defaultForShipping=true")
    Address findDefaultByCustomer(Integer customerId);
}
