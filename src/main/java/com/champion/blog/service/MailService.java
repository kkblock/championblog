package com.champion.blog.service;

import com.champion.blog.exception.ApiErrorInfoException;
import com.champion.blog.exception.GlobalErrorInfoException;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content) throws ApiErrorInfoException, GlobalErrorInfoException;
}
