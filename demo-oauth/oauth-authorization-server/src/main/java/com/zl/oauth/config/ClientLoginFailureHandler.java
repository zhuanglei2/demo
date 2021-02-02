package com.zl.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/1/29 10:24
 */
@Slf4j
@Component
public class ClientLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.debug("Login failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect("/oauth/login?error=" + URLEncoder.encode(e.getLocalizedMessage(),"UTF-8"));
    }
}
