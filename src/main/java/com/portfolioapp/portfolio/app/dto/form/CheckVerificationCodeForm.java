
package com.portfolioapp.portfolio.app.dto.form;

import lombok.Data;

@Data
public class CheckVerificationCodeForm {

    private String email;
    private String verificationCode;
}
