package com.myshop.admin.order;

import com.myshop.admin.ListInfoUtils;
import com.myshop.admin.customer.CustomerService;
import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Order;
import com.myshop.common.exception.OrderNotFoundException;
import com.myshop.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.myshop.admin.ListInfoUtils.setInfoList;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService orderService;


    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "orderTime", "asc", null);
    }


    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model,
                             @PathVariable(value = "pageNum") int pageNum,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Order> page = orderService.listByPage(pageNum,  sortDir,sortField, keyword);
        List<Order> list = page.getContent();
        int startCount = (pageNum - 1) * OrderService.PAGE_SIZE + 1;
        long endCount = startCount + OrderService.PAGE_SIZE - 1;
        ListInfoUtils.setInfoList(model, pageNum, sortField, sortDir, keyword, endCount, page, startCount, list);
        return "orders/orders";
    }

    @GetMapping("/{orderId}/view_detail")
    public String viewOrderDetail(Model model,@PathVariable("orderId") Long id, RedirectAttributes ra) {
        try {
            Order order = orderService.get(id);
            model.addAttribute("order",order);
            return "orders/modal_order_details";
        } catch (OrderNotFoundException e) {

            ra.addFlashAttribute("message_error",e.getMessage());
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


}
