package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalHeader extends AbstractHeader{

    public static final int headerLength = 5;
    private final static String valuePattern = "[0-9]{5}";
    private String value;

    private DigitalHeader(String value) {
        this.value = value;
    }

    //@Override
    public static DigitalHeader ofString (String data){
        String _value = data.substring(0, headerLength);
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(_value);
        if (matcher.find()) {
            return new DigitalHeader(_value);
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

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
