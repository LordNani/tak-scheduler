package com.simpletak.takscheduler.api.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
public class LogAspectExpressions {
    @Pointcut(value= "execution(* com.simpletak.takscheduler.api.service.*.*.*(..))")
    public void logDisplayedTime() {
    }
}
