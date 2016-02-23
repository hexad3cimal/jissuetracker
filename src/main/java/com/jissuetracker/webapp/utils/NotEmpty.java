package com.jissuetracker.webapp.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jovin on 18/12/15.
 */
public class NotEmpty {



    public static boolean notEmpty( String string ){
        return !(string == null || string.trim().length() == 0);
    }

    public static boolean notEmpty( Map<?, ?> map ){
        return !(map == null || map.isEmpty());
    }


    public static boolean notEmpty( Object object ){
        return object != null;
    }

    public static boolean notEmpty( Collection<?> collection ){
        return !(collection == null || collection.isEmpty());
    }



}
