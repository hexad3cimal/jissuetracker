package com.jissuetracker.webapp.configurations;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.springframework.stereotype.Component;

/**
 * Created by jovin on 1/5/16.
 */
@Component
public class CustomCharacterEscapes extends CharacterEscapes {


    private final int[] ESCAPES;

    public CustomCharacterEscapes() {
        ESCAPES = standardAsciiEscapesForJSON();
        for(int i=0;i<ESCAPES.length;i++) {
            if(!(Character.isAlphabetic(i) || Character.isDigit(i))) {
                ESCAPES[i] = CharacterEscapes.ESCAPE_CUSTOM;
            }
        }
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        String unicode = String.format("\\u%04x", ch);
        return new SerializedString(unicode);
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return ESCAPES;
    }
}
