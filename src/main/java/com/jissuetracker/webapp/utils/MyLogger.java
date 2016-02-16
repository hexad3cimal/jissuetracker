package com.jissuetracker.webapp.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by jovin on 12/2/16.
 */
@Aspect
public class MyLogger {

    private Logger log = Logger.getLogger(getClass());

    @Before("execution(* com.jissuetracker.webapp.controllers.MainController.home(..))")
    public void log(JoinPoint point) {
        log.info(point.getSignature().getName() + " Aspect called...");
    }


    @Before("execution(* com.jissuetracker.webapp.controllers.ProjectController.userDropdown(..))")
    public void userDropdown(JoinPoint point) {
        log.info(point.getSignature().getName() + " Aspect called...");
    }
}
