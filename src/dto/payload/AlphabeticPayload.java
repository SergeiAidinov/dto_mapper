package dto.payload;

import annotation.Header;
import dto.header.DigitalHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Header(header = DigitalHeader.class)
public class AlphabeticPayload extends AbstractPayload {
    private static String patternRegEx = "[a-zA-Z]{1,}";
    private String name;
    private String surname;

    private AlphabeticPayload(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static AlphabeticPayload ofString(String data) {
        int columnPosition = data.indexOf(':');
        String _name = data.substring(0, columnPosition);
        String _surname = data.substring(columnPosition + 1);
        Pattern pattern = Pattern.compile(patternRegEx);
        Matcher matcher = pattern.matcher(_name);
        if (!matcher.matches()) return null;
        matcher = pattern.matcher(_surname);
        if (!matcher.matches()) return null;
        return new AlphabeticPayload(_name, _surname);
    }
}
