package com.champion.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Controller 异常切面
     * @param e Exception
     * @return 要跳转得页面
     */
    @ExceptionHandler(value = TipException.class)
    public String tipException(Exception e) {
        LOGGER.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return "comm/error_500";
    }

    /**
     * Controller 异常切面
     * @param e Exception
     * @return 要跳转得页面
     */
    @ExceptionHandler(value = Exception.class)
    public String exception(Exception e){
        LOGGER.error("find exception:e={}",e.getMessage());
        e.printStackTrace();
        return "comm/error_404";
    }

    /**
     * 切 ApiErrorInfoException 异常
     * @param exception ApiErrorInfoException
     * @param response HttpServletResponse
     * @return ResultBody
     */
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

    /**
     * 切 GlobalErrorInfoException 异常
     * @param exception ApiErrorInfoException
     * @param request HttpServletRequest
     * @return ResultBody
     */
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public ResultBody errorHandlerOverJson(HttpServletRequest request, GlobalErrorInfoException exception) {
        ErrorInfoInterface errorInfo = exception.getErrorInfo();
        ResultBody result = new ResultBody(errorInfo);
        return result;
    }
}
