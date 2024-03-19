package edu.hdu.exception;

/**
 * 用户已注册异常
 */
public class RoleDeleteFailedException extends BaseException{
    public RoleDeleteFailedException() {
    }

    public RoleDeleteFailedException(String msg) {
        super(msg);
    }
}
