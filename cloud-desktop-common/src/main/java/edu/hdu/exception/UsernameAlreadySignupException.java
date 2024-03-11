package edu.hdu.exception;

/**
 * 用户已注册异常
 */
public class UsernameAlreadySignupException extends BaseException{
    public UsernameAlreadySignupException() {
    }

    public UsernameAlreadySignupException(String msg) {
        super(msg);
    }
}
