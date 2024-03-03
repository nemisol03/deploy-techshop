package com.myshop.site.checkout;

import com.myshop.common.entity.*;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.address.AddressService;
import com.myshop.site.customer.CustomerService;
import com.myshop.site.order.OrderService;
import com.myshop.site.setting.EmailSettingBag;
import com.myshop.site.setting.SettingService;
import com.myshop.site.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShoppingCartService cartService;

    @Value("${app.payment.paypal.client-id}")
    private String paypalClientId;

    @Value("${app.payment.paypal.client-secret}")
    private String paypalClientSecret;

    @GetMapping
    public String checkout(Model model) {
        Customer customer = null;
        try {
            customer = Utility.getCustomerLoggedIn(customerService );
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        }
        List<CartItem> cartItems = cartService.listCartItem(customer);
        float totalCost =0;
        for( var item : cartItems) {
            Product product = item.getProduct();
            totalCost += product.getPriceWithDiscountPercent() * item.getQuantity();
        }

        //required by PayPal format currency
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        decimalFormat.setGroupingUsed(false);
        String totalFormated = decimalFormat.format(totalCost);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        String shippingAddress = defaultAddress!=null ? defaultAddress.toString() : customer.getFullAddress();
        model.addAttribute("shippingAddress",shippingAddress);
        model.addAttribute("paypalClientId",paypalClientId);
        model.addAttribute("customer",customer);
        model.addAttribute("paypalClientSecret",paypalClientSecret);
        model.addAttribute("totalFormated",totalFormated);


        return "checkout/checkout";
    }


}
