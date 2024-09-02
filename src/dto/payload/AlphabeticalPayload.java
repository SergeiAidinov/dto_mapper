package dto.payload;

import annotation.Header;
import dto.header.DigitalHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Header(header = DigitalHeader.class)
public class AlphabeticalPayload extends AbstractPayload {
    private String patternRegEx = "[a-zA-Z]{1,}";
    private String name;
    private String surname;

    public AlphabeticalPayload ofString(String data) {
        int columnPosition = data.indexOf(':');
        name = data.substring(0, columnPosition);
        surname = data.substring(columnPosition + 1);
        Pattern pattern = Pattern.compile(patternRegEx);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) return null;
        matcher = pattern.matcher(surname);
        if (!matcher.matches()) return null;
        return this;
    }

}
