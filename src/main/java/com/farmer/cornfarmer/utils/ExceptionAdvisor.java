package com.farmer.cornfarmer.utils;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice   // 전역 설정을 위한 annotaion
@RestController
public class ExceptionAdvisor {
    //MethodArgumentNotValidException 처리를 위한 메소드
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
        }

        return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<String> processBaseError(BaseException exception) {
        return new BaseResponse<>(exception.getStatus());
    }
}
