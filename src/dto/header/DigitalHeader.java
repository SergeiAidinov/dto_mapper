package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalHeader extends AbstractHeader{

    private final int headerLength = 5;
    private final String valuePattern = "[0-9]{5}";
    private String value;

    @Override
    public DigitalHeader ofString (String data){
        value = data.substring(0, headerLength);
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

    public int getHeaderLength() {
        return headerLength;
    }
}
