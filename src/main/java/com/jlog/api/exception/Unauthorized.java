package com.jlog.api.exception;

/**
 * status -> 404
 */
public class Unauthorized extends JlogException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }


    @Override
    public int getStatusCode() {
        return 401;
    }
}
