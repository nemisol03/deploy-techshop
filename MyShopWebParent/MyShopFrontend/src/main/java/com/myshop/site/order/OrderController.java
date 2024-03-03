package com.myshop.site.order;

import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Order;
import com.myshop.common.exception.ApiPaypalException;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.common.exception.OrderNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.checkout.paypal.PaypalService;
import com.myshop.site.customer.CustomerService;
import com.myshop.site.setting.EmailSettingBag;
import com.myshop.site.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PaypalService paypalService;


    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "orderTime", "asc", null);
    }


    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model,
                             @PathVariable(value = "pageNum") int pageNum,
                             @RequestParam(value = "sortField", required = false, defaultValue = "orderTime") String sortField,
                             @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                             @RequestParam(value = "keyword", required = false) String keyword) {

        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            Page<Order> page = orderService.listByPage(pageNum, sortDir, sortField, keyword, customer);
            List<Order> list = page.getContent();
            int startCount = (pageNum - 1) * OrderService.PAGE_SIZE + 1;
            long endCount = startCount + OrderService.PAGE_SIZE - 1;
            Utility.setInfoList(model, pageNum, sortField, sortDir, keyword, endCount, page, startCount, list);
            return "orders/orders";
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        }

    }

    @GetMapping("/{orderId}/view_detail")
    public String viewOrderDetail(Model model, @PathVariable("orderId") Long id, RedirectAttributes ra) {
        try {
            Order order = orderService.get(id);
            model.addAttribute("order", order);
            return "orders/modal_order_details";
        } catch (OrderNotFoundException e) {

            ra.addFlashAttribute("message_error", e.getMessage());
            return "redirect:";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("message_success", "The order has been deleted successfully");
        } catch (OrderNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }

        return "redirect:/orders";
    }

    @PostMapping("/place_order")
    public String createOrder(HttpServletRequest req,Model model,RedirectAttributes ra) {
        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            String paymentMethod = req.getParameter("payment_method");
            Order order = orderService.saveOrder(customer,paymentMethod);
            sendVerificationEmail(order,customer.getEmail());

        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        } catch (MessagingException | UnsupportedEncodingException e) {
            ra.addFlashAttribute("message","Could not send email");
            return "redirect:checkout";
        }

        model.addAttribute("message", "Order placed successfully. We will send an email for you!");
        return "notification";
    }

    @PostMapping("/process_paypal_order")
    public String processPaypalOrder(HttpServletRequest req,Model model,RedirectAttributes ra) {
        String orderId = req.getParameter("orderId");
        try {
            if(paypalService.validateOrder(orderId)) {
                return createOrder(req,model,ra);
            }
        } catch (ApiPaypalException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }

        return "notification";
    }





    private void sendVerificationEmail(Order order, String email) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBag = settingService.listEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBag);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy");
        String toAddress = email;
        String mailSubject = "Order Notification";
        // Construct the email content
        String mailContent = "<p>Hello,</p>" +
                "<p>We hope this message finds you well. You have recently placed an order with us, and we're sending you this notification regarding your purchase.</p>" +
                "<p>Below are the details of your order:</p>" +
                "<ul>" +
                "<li><strong>Order Total:</strong> $" + order.getTotal() + "</li>" +
                "<li>Payment method: " + order.getPaymentMethod().name() + "</li> " +
                "<li><strong>Order time:</strong> " + order.getOrderTime().format(formatter) + "</li>" +
                "</ul>" +
                "<p>Thank you for choosing our services. We appreciate your business!</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // Enable HTML content
        helper.setFrom(emailSettingBag.getMailFrom(), emailSettingBag.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

}
