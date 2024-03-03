package com.myshop.site.checkout.paypal;


import java.util.Objects;

public class PaypalOrderResponse {
    private String id;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean validate(String orderId) {
        return orderId.equals(id) && status.equals("COMPLETED");
    }

    @Override
    public String toString() {
        return "PaypalOrderResponse{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
