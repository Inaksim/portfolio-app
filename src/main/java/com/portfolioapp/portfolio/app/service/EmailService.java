package com.portfolioapp.portfolio.app.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);

}
