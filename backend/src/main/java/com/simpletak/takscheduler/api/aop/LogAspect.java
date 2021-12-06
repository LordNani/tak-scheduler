package com.simpletak.takscheduler.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

//@Aspect
//@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("com.simpletak.takscheduler.api.aop.LogAspectExpressions.logDisplayedTime()")
    public Object aroundMethods(ProceedingJoinPoint theProceedingJoinPoint) {
        String method = theProceedingJoinPoint.getSignature().toShortString();
        //get the method arguments
        Object[] args = theProceedingJoinPoint.getArgs();

        //loop through args
        String methodName = theProceedingJoinPoint.getSignature().getName();
        for (Object tempArg : args) {
            logger.info("\n=======>>>> Executing @Around on method: {}. Argument: {}", method, tempArg);
        }
        try {
            Object result = theProceedingJoinPoint.proceed();
            logger.info("\n=======>>>> Executing @Around on method: {}. Result: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Aspect error", throwable);
        }
        return null;
    }

    @Before("com.simpletak.takscheduler.api.aop.LogAspectExpressions.logDisplayedTime()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String date = sdf.format(new Timestamp(System.currentTimeMillis()));
        String method = theJoinPoint.getSignature().toShortString();
        logger.info("\n=======>>>> Executing @Before on method: {}. Time: {}", method, date);
    }
}
