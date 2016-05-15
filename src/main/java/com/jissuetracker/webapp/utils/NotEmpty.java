package com.jissuetracker.webapp.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jovin on 18/12/15.
 */

//Custom class for null check
public class NotEmpty {

    //String
    public static boolean notEmpty( String string ){
        return !(string == null || string.trim().length() == 0);
    }

    //Map
    public static boolean notEmpty( Map<?, ?> map ){
        return !(map == null || map.isEmpty());
    }

    //Object
    public static boolean notEmpty( Object object ){
        return object != null;
    }

    //Collection
    public static boolean notEmpty( Collection<?> collection ){
        return !(collection == null || collection.isEmpty());
    }



}
