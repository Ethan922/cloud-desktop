package edu.hdu.exception;

/**
 * 用户已注册异常
 */
public class UsernameOccupiedException extends BaseException{
    public UsernameOccupiedException() {
    }

    public UsernameOccupiedException(String msg) {
        super(msg);
    }
}
