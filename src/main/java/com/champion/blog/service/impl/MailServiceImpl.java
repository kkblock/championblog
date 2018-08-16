package com.champion.blog.service.impl;

import com.champion.blog.exception.ApiErrorInfoException;
import com.champion.blog.exception.GlobalErrorInfoException;
import com.champion.blog.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    public static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public void sendSimpleMail(String to, String subject, String content) throws ApiErrorInfoException, GlobalErrorInfoException {

    }
}
