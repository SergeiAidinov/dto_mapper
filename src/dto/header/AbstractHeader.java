package dto.header;

public abstract class AbstractHeader <H extends AbstractHeader> implements Comparable<H>{

    public static final int headerLength = 0;
    public static AbstractHeader ofString (String data){
        return null;
    }

    public int getHeaderLength() {
        return headerLength;
    }
}
