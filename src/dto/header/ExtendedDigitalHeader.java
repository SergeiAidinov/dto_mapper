package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtendedDigitalHeader extends DigitalHeader {

    private final String valuePattern = "[0-9]{7}";
    private String value;

    @Override
    public DigitalHeader ofString (String data){
        value = data.substring(0,6);
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return this;
        } else {
            return null;
        }
    }

    public String getValue() {
        return value;
    }
}
