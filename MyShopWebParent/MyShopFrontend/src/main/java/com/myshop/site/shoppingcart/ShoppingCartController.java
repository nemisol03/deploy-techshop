package com.myshop.site.shoppingcart;

import com.myshop.common.entity.Address;
import com.myshop.common.entity.CartItem;
import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.address.AddressService;
import com.myshop.site.customer.CustomerService;
import com.myshop.site.oauth.CustomerOAuth2User;
import com.myshop.site.security.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/carts")
public class ShoppingCartController {


    @Autowired private ShoppingCartService cartService;
    @Autowired private CustomerService customerService;

    @Autowired private AddressService addressService;
    @GetMapping("")
    public String listAll(Model model)  {

        List<CartItem> cartItemList = null;
        try {
            cartItemList = cartService.listCartItem(getAuthenticatedCustomer());
            Customer customer = Utility.getCustomerLoggedIn(customerService );
            Address defaultAddress = addressService.getDefaultAddress(customer);
            String shippingAddress = defaultAddress!=null ? defaultAddress.toString() : customer.getFullAddress();
            model.addAttribute("shippingAddress",shippingAddress);
        model.addAttribute("cartItemList",cartItemList);
        } catch (CustomerNotFoundException e) {
            return "/login";
        }
        float costTotal = 0;
        for(var item : cartItemList) {
            costTotal +=item.getSubTotal();
        }


        model.addAttribute("costTotal",costTotal);

        return "carts/shopping_cart";
    }

    public Customer getAuthenticatedCustomer() throws CustomerNotFoundException {
        return Utility.getCustomerLoggedIn(customerService);
    }


}
