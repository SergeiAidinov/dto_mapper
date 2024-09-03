package annotation;

import dto.header.AbstractHeader;
import dto.header.DigitalHeader;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {
    Class<? extends AbstractHeader>[] headers();
}
