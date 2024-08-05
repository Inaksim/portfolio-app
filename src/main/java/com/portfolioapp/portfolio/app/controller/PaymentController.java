package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestParam String token, @RequestParam int amount) {
        try {
            Charge charge = paymentService.chargeCreditCard(token, amount);
            return ResponseEntity.ok(charge.getId());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> createCheckoutSession(@RequestParam String successUrl, @RequestParam String cancelUrl) {
        try {
            Session session = paymentService.createCheckoutSession(successUrl, cancelUrl);
            return ResponseEntity.ok(session.getUrl());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
