package com.jissuetracker.webapp.utils;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jovin on 10/2/16.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response exception(Exception e) {

        e.printStackTrace();
        return new Response(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e)   {

        return "404";
    }

    @ExceptionHandler(HibernateException.class)
    public Response hibernateException(Exception e)
    {
        return new Response(e.getMessage());
    }

}
