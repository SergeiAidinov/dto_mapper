package dto.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtendedDigitalHeader<H extends AbstractHeader> extends AbstractHeader {

    public static final int headerLength = 7;
    private static final String valuePattern = "[0-9]{7}";
    private String value;

    private ExtendedDigitalHeader(String value) {
        this.value = value;
    }

    //@Override
    public static ExtendedDigitalHeader ofString (String data){
        String _value = data.substring(0,6);
        Pattern pattern = Pattern.compile(valuePattern);
        Matcher matcher = pattern.matcher(_value);
        if (matcher.find()) {
            return new ExtendedDigitalHeader(_value);
        } else {
            return null;
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        final Integer thisHeaderLength = headerLength;
        final H otherHeader = (H) o;
        final Integer otherHeaderLength = otherHeader.getHeaderLength();
        return thisHeaderLength.compareTo(otherHeaderLength);
    }
}
