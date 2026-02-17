package com.gabryel.api_funcionarios.aspect;

import com.gabryel.api_funcionarios.StartApp;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
//@Slf4j
public class LogginAspect {
    private static final Logger logger = LoggerFactory.getLogger(StartApp.class);

    @Pointcut("excution(public * com.gabryel.api_funcionarios.controller.*.*(..))")
    public void controllerLog() {
    }

    @Pointcut("excution(public * com.gabryel.api_funcionarios.controller.*.*(..))")
    public void serviceLog() {
    }


    @Before("controllerLog()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes atributos = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (atributos != null) {
            request = atributos.getRequest();
        }

        if (request != null) {
            logger.info("Request: IP: {}, URL: {}, HTTP_METHOD: {}, CONTROLLER_METHOD: {}.{}",
                    request.getRemoteAddr(),
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
        }
    }
}
