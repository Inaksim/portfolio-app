package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


import java.util.HashMap;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public Charge chargeCreditCard(String token, int amount) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }

    @Override
    public Session createCheckoutSession(String successUrl, String cancelUrl) throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .build();

        return Session.create(params);
    }
}
