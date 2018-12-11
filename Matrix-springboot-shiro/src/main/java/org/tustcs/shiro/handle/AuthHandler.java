package org.tustcs.shiro.handle;

import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AuthHandler {
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        return new ResponseBean().error(401, e.getMessage());
    }

    // 捕捉UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401() {
        return new ResponseBean().error(401, "Unauthorized");
    }

    @ExceptionHandler(SUserException.class)
    public ResponseBean handle(SUserException e) {
        return new ResponseBean().error(401, e.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        return new ResponseBean().error(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
