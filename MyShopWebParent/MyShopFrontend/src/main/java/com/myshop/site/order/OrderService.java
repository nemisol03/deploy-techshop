package com.myshop.site.order;


import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Order;
import com.myshop.common.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderService {
    public final static int PAGE_SIZE = 2;

    @Autowired
    private OrderClientRepository orderRepo;
    public Page<Order> listByPage(int pageNo, String sortDir, String sortField, String keyword, Customer customer) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNo- 1,PAGE_SIZE,sort);
        Page<Order> page;
        if(keyword!=null && !keyword.isEmpty()) {
            page = orderRepo.search(keyword,customer.getId(),pageable);
        }else {
            page = orderRepo.findAllByCustomer(pageable,customer);
        }
        return page;

    }

    public Order get(Long id) throws OrderNotFoundException {
        try {
            return orderRepo.findById(id).get();
        }catch(NoSuchElementException ex) {
            throw new OrderNotFoundException("Could not find any order with the given id: " + id);
        }
    }

    public void deleteOrder(Long id) throws OrderNotFoundException {
        get(id);
        orderRepo.deleteById(id);
    }
}
