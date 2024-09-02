package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabeticHeader extends AbstractHeader{
    private final int headerLength = 6;
    private final String valuePattern = "[a-zA-Z]{6}";
    private String value;

    @Override
    public AlphabeticHeader ofString (String data){
        value = data.substring(0,headerLength);
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
