package com.champion.blog.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiErrorInfoException.class)
    @ResponseBody
    public ResultBody errorHandlerOverJson(ApiErrorInfoException exception, HttpServletResponse response) {
        ResultBody resultBody = new ResultBody();
        Long errorCode = exception.getErrorCode();
        String message = exception.getMessage();
        Object data = exception.getData();
        resultBody.setCode(errorCode);
        resultBody.setMessage(message);
        resultBody.setResult(data);
        return resultBody;
    }
}


