package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabeticHeader extends AbstractHeader {
    public static final int headerLength = 6;
    private static final String valuePattern = "[a-zA-Z]{6}";
    private String value;

    private AlphabeticHeader(String value) {
        this.value = value;
    }

    public static AlphabeticHeader ofString(String data){
        String _value = data.substring(0,headerLength);
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(_value);
        if (matcher.find()) {
            return new AlphabeticHeader(_value);
        } else {
            return null;
        }
    }

    public String getValue() {
        return value;
    }
}
