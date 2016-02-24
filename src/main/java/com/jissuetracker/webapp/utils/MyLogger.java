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

    @Before("execution(* com.jissuetracker.webapp.dao.IssueDaoImpl.add(..))")
    public void issueAddDao(JoinPoint point) {
        log.info(point.getSignature().getName()
                + " called with arguments "+ point.getArgs());
    }

    @Before("execution(* com.jissuetracker.webapp.dao.IssueDaoImpl.update(..))")
    public void issueUpdateDao(JoinPoint point) {
        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());
    }

    @Before("execution(* com.jissuetracker.webapp.services.IssueServiceImpl.add(..))")
    public void issueAddService(JoinPoint point) {
        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());
    }

    @Before("execution(* com.jissuetracker.webapp.services.IssueServiceImpl.update(..))")
    public void issueUpdateService(JoinPoint point) {
        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());
    }


    @Before("execution(* com.jissuetracker.webapp.controllers.IssueController.addIssue(..))")
    public void issueControllerAddIssue(JoinPoint point) {
        log.info(point.getSignature().getName() + " called ");
    }

    @Before("execution(* com.jissuetracker.webapp.dao.UserDaoImpl.getUserById(..))")
    public void userById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }

    @Before("execution(* com.jissuetracker.webapp.services.UserServiceImpl.getUserById(..))")
    public void userServiceById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }

    @Before("execution(* com.jissuetracker.webapp.dao.TrackerDaoImpl.getById(..))")
    public void trackerById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }


    @Before("execution(* com.jissuetracker.webapp.services.TrackerServiceImpl.getById(..))")
    public void trackerServiceById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }


    @Before("execution(* com.jissuetracker.webapp.services.StatusServiceImpl.getById(..))")
    public void statusServiceById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }

    @Before("execution(* com.jissuetracker.webapp.dao.StatusDaoImpl.getById(..))")
    public void statusById(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }


    @Before("execution(* com.jissuetracker.webapp.dao.StatusDaoImpl.add(..))")
    public void addStatus(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }


    @Before("execution(* com.jissuetracker.webapp.dao.StatusDaoImpl.update(..))")
    public void updateStatus(JoinPoint point) {

        log.info(point.getSignature().getName()
                + "  called with arguments "+ point.getArgs());    }
}
