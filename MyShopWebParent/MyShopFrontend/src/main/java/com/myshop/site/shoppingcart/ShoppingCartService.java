package com.myshop.site.shoppingcart;

import com.myshop.common.entity.CartItem;
import com.myshop.common.entity.Customer;
import com.myshop.common.entity.Product;
import com.myshop.common.exception.ShoppingCartMaxQuantityExceeded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional

public class ShoppingCartService {
    @Autowired
    private CartItemRepository cartItemRepository;


    public int addProduct(Customer customer, Integer productId, int quantity) throws ShoppingCartMaxQuantityExceeded {
        int updatedQuantity = quantity;
        Product product = new Product(productId);
        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);
        if (cartItem != null) {
            // cart item already exist
            updatedQuantity = cartItem.getQuantity() + quantity;
            if (updatedQuantity > 10) {
                throw new ShoppingCartMaxQuantityExceeded("Shopping cart quantity exceeded the maximum limit of 10." +
                        "Currently, your shopping cart has been contained " + cartItem.getQuantity() + " item(s) like this");
            }
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCustomer(customer);
        }
        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);
        return updatedQuantity;
    }

    public List<CartItem> listCartItem(Customer customer) {
        return  cartItemRepository.findByCustomer(customer);
    }

    public Float updateQuantity(Customer customer,Integer productId,int quantity) {
        cartItemRepository.updateQuantity(customer, productId,quantity);
        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer,new Product(productId));

        return cartItem.getSubTotal();
    }

    public void removeCartItem(Integer id) {
        cartItemRepository.deleteById(id);
    }

    public void deleteByCustomer(Customer customer) {
        cartItemRepository.deleteByCustomer(customer);
    }


}
