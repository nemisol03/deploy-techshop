package com.myshop.site.checkout;

import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CheckoutService checkoutService;
    @GetMapping
    public String checkout(Model model) {
        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            checkoutService.saveOrder(customer);
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        }

        model.addAttribute("message", "Order placed successfully. Please pay attention to your phone!");
        return "notification";
    }
}
