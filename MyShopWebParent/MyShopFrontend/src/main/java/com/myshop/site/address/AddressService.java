package com.myshop.site.address;

import com.myshop.common.entity.Address;
import com.myshop.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AddressService {
    @Autowired
    private AddressRepository addressRepo;

    public List<Address> getAddressByCustomer(Integer id) {
        return addressRepo.findByCustomerId(id);
    }


    public Address get(Integer customerId,Integer addressId) {
        return addressRepo.findByCustomerIdAndId(customerId,addressId);
    }
    public Address save(Address address) {
        return addressRepo.save(address);
    }

    public void delete(Integer id) {
         addressRepo.deleteById(id);
    }

    public void setDefaultAddress(Integer defaultAddressId,Integer customerId) {
        // in case default address's id is greater than -> not set primary else don't set specify any address
        if(defaultAddressId>0) {
        addressRepo.setDefaultAddress(defaultAddressId);
        }
        addressRepo.setNonDefaultForOthers(defaultAddressId,customerId);
    }

    public Address getDefaultAddress(Customer customer) {
        return addressRepo.findDefaultByCustomer(customer.getId())  ;
    }
}
