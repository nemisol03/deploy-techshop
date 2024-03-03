package com.myshop.site.checkout;

import com.myshop.common.entity.*;
import com.myshop.site.Utility;
import com.myshop.site.address.AddressService;
import com.myshop.site.customer.OrderDetailRepository;
import com.myshop.site.customer.OrderRepository;
import com.myshop.site.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CheckoutService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @Autowired
    private AddressService addressService;
    @Autowired
    private ShoppingCartService cartService;



}
