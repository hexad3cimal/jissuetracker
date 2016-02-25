package com.jissuetracker.webapp.utils;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jovin on 25/2/16.
 */

public class CustomDateFormatter implements PropertyEditorRegistrar
{
    public void registerCustomEditors(PropertyEditorRegistry registry)
    {
        registry.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }
}