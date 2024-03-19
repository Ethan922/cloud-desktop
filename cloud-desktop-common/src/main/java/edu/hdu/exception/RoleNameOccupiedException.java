package edu.hdu.exception;

/**
 * 用户已注册异常
 */
public class RoleNameOccupiedException extends BaseException{
    public RoleNameOccupiedException() {
    }

    public RoleNameOccupiedException(String msg) {
        super(msg);
    }
}
