package com.myshop.site.address;

import com.myshop.common.entity.Address;
import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/address_book")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String listAddress(Model model) throws CustomerNotFoundException {
        Customer logged = Utility.getCustomerLoggedIn(customerService);
        List<Address> addressList = addressService.getAddressByCustomer(logged.getId());
        boolean usePrimaryAddressAsDefault = true;
        for (var address : addressList) {
            if (address.isDefaultForShipping()) {
                usePrimaryAddressAsDefault = false;
                break;
            }
        }

        model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
        model.addAttribute("customer", logged);
        model.addAttribute("addressList", addressList);
        return "address_book/addresses";
    }

    @GetMapping("/create_new")
    public String createAddress(Model model) {
        model.addAttribute("addressObj", new Address());
        model.addAttribute("pageTitle", "Create new address");
        return "address_book/address_book_form";
    }

    @GetMapping("/edit/{id}")
    public String editAddress(Model model,@PathVariable("id") Integer addressId) {
        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            Address address = addressService.get(customer.getId(), addressId);
            model.addAttribute("addressObj",address);
            model.addAttribute("pageTitle","Edit address with id: " + addressId );
            return "address_book/address_book_form";
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        }

    }

    @PostMapping("/save")
    public String saveAddress(@ModelAttribute("addressObj") Address address, RedirectAttributes ra) {

        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            address.setCustomer(customer);
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";
        }
        addressService.save(address);
         ra.addFlashAttribute("message_success","Saved address successfully!");
        return "redirect:/address_book";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Integer id, RedirectAttributes ra) {

        addressService.delete(id);
        ra.addFlashAttribute("message_success","Deleted address successfully!");
        return "redirect:/address_book";
    }

    @GetMapping("/default/{id}")
public String setDefaultShipping(@PathVariable("id") Integer addressId) {
        try {
            Customer customer = Utility.getCustomerLoggedIn(customerService);
            addressService.setDefaultAddress(addressId,customer.getId());
            return "redirect:/address_book";
        } catch (CustomerNotFoundException e) {
            return "redirect:/login";

        }
    }
}
