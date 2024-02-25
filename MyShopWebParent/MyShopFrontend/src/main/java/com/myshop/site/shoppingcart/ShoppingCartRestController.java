package com.myshop.site.shoppingcart;

import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.common.exception.ShoppingCartMaxQuantityExceeded;
import com.myshop.site.Utility;
import com.myshop.site.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class ShoppingCartRestController {

    @Autowired private ShoppingCartService cartService;
    @Autowired private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(HttpServletRequest request, @PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") int quantity) {
        try {
            Customer customer = getAuthenticatedCustomer();
            int updatedQuantity = cartService.addProduct(customer,productId,quantity);
            return "The product has been added to your shopping cart with a quantity of: " + updatedQuantity;
        } catch (CustomerNotFoundException e) {
            return "You must login before shopping";
        } catch (ShoppingCartMaxQuantityExceeded e) {
            return e.getMessage();
        }
    }

    public Customer getAuthenticatedCustomer() throws CustomerNotFoundException {
        return Utility.getCustomerLoggedIn(customerService);


//        Object principal = request.getUserPrincipal();
//        if(principal instanceof UsernamePasswordAuthenticationToken
//        || principal instanceof RememberMeAuthenticationToken) {
//            String email =  request.getUserPrincipal().getName();
//            return customerService.findByEmail(email);
//        }else {
//            throw new CustomerNotFoundException("No authenticated customer found ");
//        }
    }

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(HttpServletRequest request, @PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") int quantity) {
        try {
            Customer customer = getAuthenticatedCustomer();
            float updatedQuantity = cartService.updateQuantity(customer,productId,quantity);


            return String.valueOf(updatedQuantity);
        } catch (CustomerNotFoundException e) {
            return "You must login before shopping";
        }
    }

    @DeleteMapping("/cart/remove/{cartItemId}")
    public void removeCartItem(@PathVariable("cartItemId") Integer id) {
        cartService.removeCartItem(id);
    }
}
