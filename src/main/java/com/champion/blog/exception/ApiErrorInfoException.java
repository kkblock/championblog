package com.champion.blog.exception;

/**
 * @Auther kuangkai
 * @Descrption 自定义异常
 */
public class ApiErrorInfoException extends RuntimeException {
    protected Long errorCode ;//异常编码
    protected Object data ;//异常数据

    public ApiErrorInfoException(Long errorCode,String message,Object data,Throwable e){
        super(message,e);
        this.errorCode = errorCode ;
        this.data = data ;
    }

    public ApiErrorInfoException(String message){
        this(null,message,null,null);
    }

    public ApiErrorInfoException(Long errorCode,String message,Object data){
        this(errorCode,message,data,null);
    }

    public ApiErrorInfoException(Long errorCode,String message){
        this(errorCode,message,null,null);
    }

    public ApiErrorInfoException(String message,Throwable e){
        this(null,message,null,e);
    }

    public ApiErrorInfoException(){

    }

    public ApiErrorInfoException(Throwable e){
        super(e);
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}