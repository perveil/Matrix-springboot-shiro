package org.tustcs.shiro.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.tustcs.shiro.exception.SUserException;

@Slf4j
class SBaseController {
    void handleError(BindingResult errors) {
        StringBuilder stringBuilder = new StringBuilder("error field:  ");
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                stringBuilder.append(fieldError.getField()).append("->").append(fieldError.getDefaultMessage()).append("   ");
            });
            log.error(stringBuilder.toString());
            throw new SUserException(555, stringBuilder.toString());
        }
    }
}
