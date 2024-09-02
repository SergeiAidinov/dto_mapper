package dto.header;

import annotation.Header;

public abstract class AbstractHeader <T extends AbstractHeader> {

    private int headerLength = 0;
    public static AbstractHeader ofString (String data){
        return null;
    }

    public int getHeaderLength() {
        return headerLength;
    }
}
