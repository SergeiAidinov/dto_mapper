import dto.header.AbstractHeader;

import java.lang.reflect.Field;
import java.util.Comparator;

public class HeaderComparator<H extends AbstractHeader> implements Comparator<Class<H>> {

    @Override
    public int compare(Class<H> o1, Class<H> o2) {
        Integer l1, l2;
        try {
            final Field field1 = o1.getDeclaredField("headerLength");
            l1 = Integer.valueOf(field1.getInt(field1));
            final Field field2 = o2.getDeclaredField("headerLength");
            l2 = Integer.valueOf(field2.getInt(field2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return l1.compareTo(l2);
    }
}
