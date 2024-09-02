package dto.payload;

import annotation.Header;
import dto.header.DigitalHeader;

@Header(header = DigitalHeader.class)
public class SimplePayload extends AbstracPayload {
}
