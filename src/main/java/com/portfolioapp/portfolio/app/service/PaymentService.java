package com.portfolioapp.portfolio.app.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;

public interface PaymentService {

    Charge chargeCreditCard(String token, int amount) throws StripeException;

    Session createCheckoutSession(String successUrl, String cancelUrl) throws StripeException;

}
