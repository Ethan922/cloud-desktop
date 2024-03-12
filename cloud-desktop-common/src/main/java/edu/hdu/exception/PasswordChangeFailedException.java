package edu.hdu.exception;

public class PasswordChangeFailedException extends BaseException{
    public PasswordChangeFailedException() {
    }

    public PasswordChangeFailedException(String msg) {
        super(msg);
    }
}
