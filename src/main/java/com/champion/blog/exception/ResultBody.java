package com.champion.blog.exception;

/**
 * @Auther kuangkai
 * @Date 2018/3/7
 * @Descrption 返回体
 */
public class ResultBody {
    /**
     * 响应代码
     */
    private Long code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {
        this.code = GlobalErrorInfoEnum.SUCCESS.getCode();
        this.message = GlobalErrorInfoEnum.SUCCESS.getMessage();
    }

    public ResultBody(ErrorInfoInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }

    public ResultBody(Long code,String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBody(Object result) {
        this.code = GlobalErrorInfoEnum.SUCCESS.getCode();
        this.message = GlobalErrorInfoEnum.SUCCESS.getMessage();
        this.result = result;
    }

    public Long getCode() {
        return code;
    }

    public ResultBody setCode(Long code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultBody setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ResultBody setResult(Object result) {
        this.result = result;
        return this;
    }
}
