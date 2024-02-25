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


    public void saveOrder(Customer customer) {
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
            orderDetail.setProductCost(product.getPrice() * item.getQuantity());
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
        order.setStatus(OrderStatus.NEW);
        order.setCustomer(customer);
        order.setOrderDetails(orderDetails);

        order.setPaymentMethod(PaymentMethod.COD);
        cartService.deleteByCustomer(customer);
        orderRepo.save(order);

    }
}
