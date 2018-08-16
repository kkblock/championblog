//package com.champion.blog.exception;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletResponse;
//
//@ControllerAdvice
//public class ExceptionHandler {
//
//    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ResultBody errorHandlerOverJson(Exception exception, HttpServletResponse response) {
//        ResultBody resultBody = new ResultBody();
//        if (exception instanceof ApiErrorInfoException) {
//            ApiErrorInfoException apiErrorInfoException = (ApiErrorInfoException) exception;
//            Long errorCode = apiErrorInfoException.getErrorCode();
//            String message = apiErrorInfoException.getMessage();
//            Object data = apiErrorInfoException.getData();
//            resultBody.setCode(errorCode);
//            resultBody.setMessage(message);
//            resultBody.setResult(data);
//            return resultBody;
//        }else if (exception instanceof GlobalErrorInfoException) {
//            GlobalErrorInfoException globalErrorInfoException = (GlobalErrorInfoException) exception;
//            ErrorInfoInterface errorInfo = globalErrorInfoException.getErrorInfo();
//            ResultBody result = new ResultBody(errorInfo);
//            return result;
//        }
//        return null;
//    }
//}
