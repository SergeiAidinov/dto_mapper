package annotation;

import dto.header.DigitalHeader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Header {
    Class<DigitalHeader> header();
}
