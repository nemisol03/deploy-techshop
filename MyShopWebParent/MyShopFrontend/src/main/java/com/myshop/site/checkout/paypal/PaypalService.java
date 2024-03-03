package com.myshop.site.checkout.paypal;

import com.myshop.common.exception.ApiPaypalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class PaypalService {
    private static final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";

    @Value("${app.payment.paypal.client-id}")
    private String clientID;
    @Value("${app.payment.paypal.client-secret}")
    private String clientSecret;

//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public boolean validateOrder(String orderId) throws ApiPaypalException {
        String requestURI = BASE_URL + GET_ORDER_API + orderId;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(clientID,clientSecret);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaypalOrderResponse> response = restTemplate.exchange(requestURI, HttpMethod.GET, request, PaypalOrderResponse.class);
        HttpStatus statusCode = response.getStatusCode();
        if(!statusCode.equals(HttpStatus.OK)) {

        switch (statusCode) {
            case NOT_FOUND -> throw new ApiPaypalException("Order ID not found");
            case BAD_REQUEST -> throw new ApiPaypalException("invalid information");
            case INTERNAL_SERVER_ERROR -> throw new ApiPaypalException("Paypal's server is not working");
            default -> throw new ApiPaypalException("Something went wrong!");
        }
        }
        PaypalOrderResponse body = response.getBody();
        return body.validate(orderId);
    }

}
