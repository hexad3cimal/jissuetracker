package com.jissuetracker.webapp.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jovin on 18/12/15.
 */
public class NotEmpty {



    public static boolean notEmpty( String string ){
        if( string == null || string.trim().length() == 0 ){
            return false;
        }
        return true;
    }

    public static boolean notEmpty( Map<?, ?> map ){
        if( map == null || map.isEmpty() ){
            return false;
        }
        return true;
    }


    public static boolean notEmpty( Object object ){
        if( object == null ){
            return false;
        }
        return true;
    }

    public static boolean notEmpty( Collection<?> collection ){
        if( collection == null || collection.isEmpty() ){
            return false;
        }
        return true;
    }



}
