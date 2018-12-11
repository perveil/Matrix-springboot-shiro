package org.tustcs.shiro.exception;

import org.tustcs.shiro.pojo.enums.StatusEnum;

public class SUserException extends RuntimeException {

    private int code;

    public SUserException() {
        super();
    }

    public SUserException(StatusEnum statusEnum) {
        super(statusEnum.getMsg());
        this.code = statusEnum.getCode();
    }

    public SUserException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
