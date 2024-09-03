package dto.payload;

import annotation.Header;
import dto.header.AbstractHeader;
import dto.header.AlphabeticHeader;
import dto.header.DigitalHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Header(headers = {AlphabeticHeader.class})
public class DigitalPayload extends AbstractPayload {

    private static String patternRegEx = "[0-9]{1,}";
    private String name;
    private String surname;

    private DigitalPayload(String _name, String _surname) {
        name = _name;
        surname = _surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public static DigitalPayload ofString(String data) {
        int columnPosition = data.indexOf(':');
        String _name = data.substring(0, columnPosition);
        String _surname = data.substring(columnPosition + 1);
        Pattern pattern = Pattern.compile(patternRegEx);
        Matcher matcher = pattern.matcher(_name);
        if (!matcher.matches()) return null;
        matcher = pattern.matcher(_surname);
        if (!matcher.matches()) return null;
        return new DigitalPayload(_name, _surname);
    }
}
