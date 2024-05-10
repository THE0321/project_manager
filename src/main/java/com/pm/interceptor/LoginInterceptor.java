package com.pm.interceptor;

import com.pm.values.constValues;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();
        boolean isLoginPage = requestUri.contains("/login") || requestUri.contains("/find_password") || requestUri.contains("/error");

        if(requestMethod.toUpperCase().equals("GET")) {
            // 로그인 정보가 없는 경우 로그인 페이지로
            if(request.getSession().getAttribute(constValues.LOGIN_SESSION) == null && !isLoginPage){
                response.sendRedirect("/login");
                return false;
            }

            // 로그인 정보가 있는 경우 메인 페이지로
            if(request.getSession().getAttribute(constValues.LOGIN_SESSION) != null && isLoginPage) {
                response.sendRedirect("/");
                return false;
            }
        }

        return true;
    }
}