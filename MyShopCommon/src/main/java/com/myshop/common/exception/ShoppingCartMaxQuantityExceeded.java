package com.myshop.common.exception;

public class ShoppingCartMaxQuantityExceeded extends Throwable {
    public ShoppingCartMaxQuantityExceeded(String message) {
        super(message);
    }
}
