package com.portfolioapp.portfolio.app.exception;

import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {

    PROJECT_NOT_FOUND( "PROJECT_NOT_FOUND", HttpStatus.NOT_FOUND , "Project with id ${id} not found"),
    FAVORITE_PROJECT_ALREADY_EXIST( "FAVORITE_PROJECT_ALREADY_EXIST", HttpStatus.CONFLICT , "User already has favorite project with id ${projectId}"),
    FAVORITE_PROJECT_NOT_FOUND( "FAVORITE_PROJECT_NOT_FOUND", HttpStatus.NOT_FOUND , "User has not favorite project with id ${projectId}"),
    TAG_NOT_FOUND("TAG_NOT_FOUND", HttpStatus.NOT_FOUND , "Tag with id ${id} not found"),
    TAG_ALREADY_EXIST("TAG_ALREADY_EXIST", HttpStatus.CONFLICT , "Tag with name ${name} already exist"),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND , "User with id ${id} not found"),
    USER_NOT_FOUND_BY_EMAIL("USER_NOT_FOUND", HttpStatus.NOT_FOUND , "User with email ${email} not found"),
    USER_ALREADY_EXIST("USER_ALREADY_EXIST", HttpStatus.CONFLICT , "User with email ${email} already exist"),
    VERIFICATION_CODE_NOT_MATCH("VERIFICATION_CODE_NOT_MATCH", HttpStatus.BAD_REQUEST , "Verification code doesn't match"),
    BAD_CREDENTIALS("BAD_CREDENTIALS", HttpStatus.BAD_REQUEST , "Bad Credentials"),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "User is unauthorized"),
    SERVER_ERROR("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Unexpected issue...");



    final String key;
    final HttpStatus httpStatus;
    final String message;

    Errors(String key, HttpStatus httpStatus, String message) {
        this.message = message;
        this.key = key;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
