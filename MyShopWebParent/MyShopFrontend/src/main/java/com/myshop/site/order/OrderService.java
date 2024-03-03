package com.myshop.site.order;


import com.myshop.common.entity.*;
import com.myshop.common.exception.OrderNotFoundException;
import com.myshop.site.address.AddressService;
import com.myshop.site.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional

public class OrderService {
    public final static int PAGE_SIZE = 2;

    @Autowired
    private OrderClientRepository orderRepo;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ShoppingCartService cartService;

    public Page<Order> listByPage(int pageNo, String sortDir, String sortField, String keyword, Customer customer) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        Page<Order> page;
        if (keyword != null && !keyword.isEmpty()) {
            page = orderRepo.search(keyword, customer.getId(), pageable);
        } else {
            page = orderRepo.findAllByCustomer(pageable, customer);
        }
        return page;

    }

    public Order get(Long id) throws OrderNotFoundException {
        try {
            return orderRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new OrderNotFoundException("Could not find any order with the given id: " + id);
        }
    }

    public void deleteOrder(Long id) throws OrderNotFoundException {
        get(id);
        orderRepo.deleteById(id);
    }

    public Order saveOrder(Customer customer, String paymentMethod) {
        Order order = new Order();
        List<CartItem> cartItemList = cartService.listCartItem(customer);
        float costTotal = 0;
        Set<OrderDetail> orderDetails = new HashSet<>();

        for (var item : cartItemList) {
            Product product = item.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setSubtotal(item.getSubTotal());
            orderDetail.setProductCost(product.getPriceWithDiscountPercent() * item.getQuantity());
            costTotal += item.getSubTotal();
            orderDetails.add(orderDetail);
        }

        Address defaultAddress = addressService.getDefaultAddress(customer);
        if (defaultAddress != null) {
            order.setFirstName(defaultAddress.getFirstName());
            order.setLastName(defaultAddress.getLastName());
            order.setPhoneNumber(defaultAddress.getPhoneNumber());
            order.setAddress(defaultAddress.getAddress());
        } else {
            order.setFirstName(customer.getFirstName());
            order.setLastName(customer.getLastName());
            order.setPhoneNumber(customer.getPhoneNumber());
            order.setAddress(customer.getAddress());
        }

        order.setTotal(costTotal);
        if (paymentMethod.equals(PaymentMethod.PAYPAL.name())) {
            order.setStatus(OrderStatus.PAID);
        } else {
            order.setStatus(OrderStatus.NEW);

        }
        order.setCustomer(customer);
        order.setOrderDetails(orderDetails);

        // customer must confirm by email before checkout
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        cartService.deleteByCustomer(customer);
        return orderRepo.save(order);

    }


}
