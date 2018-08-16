package com.champion.blog.exception;

/**
 * @Auther kuangkai
 * @Date 2018/3/7
 * @Descrption 应用系统级别的错误码
 */
public enum GlobalErrorInfoEnum implements ErrorInfoInterface {
    SUCCESS(200L, "success"),
    FAIL(400L,"fail"),
    NOT_FOUND(-1L, "service not found");

    private Long code;

    private String message;

    GlobalErrorInfoEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
