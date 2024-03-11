package edu.hdu.exception;

/**
 * 账号未启用
 */
public class AccountNotActiveException extends BaseException{
    public AccountNotActiveException() {
    }

    public AccountNotActiveException(String msg) {
        super(msg);
    }
}
