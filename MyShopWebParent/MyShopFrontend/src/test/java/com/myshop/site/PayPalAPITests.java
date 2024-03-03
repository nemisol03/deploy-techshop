package com.myshop.site;

import com.myshop.site.checkout.paypal.PaypalOrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;



public class PayPalAPITests {
    private static final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";

    private  String clientID = "AaTPWUAZ4PVzSFWouRPhdu8z_IwZNxIknecfvN_niVV9rvMePOc7g4WlJU2YUC6PDZiormRkp_JLpMB5";
    private String clientSecret = "EFFX6gtn-jNGBi0Q9FNVwYH4VvrfvRdqpt7-uZO25Eirsm-yq_hEqG6Eo0NotlvwaZP-PhQgCRNlCWSM";


    @Test
    public void testGetOrderDetails() {
        String orderId = "1A114310R97617447";
        String requestURI = BASE_URL + GET_ORDER_API + orderId;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.add("Accept")
        headers.setBasicAuth(clientID,clientSecret);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaypalOrderResponse> response = restTemplate.exchange(requestURI, HttpMethod.GET, request, PaypalOrderResponse.class);
        PaypalOrderResponse body = response.getBody();

        System.out.println(body);
    }
}
