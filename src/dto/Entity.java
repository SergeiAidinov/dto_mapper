package dto;

import dto.header.AbstractHeader;
import dto.payload.AbstractPayload;

public class Entity<H extends AbstractHeader, P extends AbstractPayload> {

    private final H header;
    private final P payload;

    public Entity(H header, P payload) {
        this.header = header;
        this.payload = payload;
    }

    public H getHeader() {
        return header;
    }

    public P getPayload() {
        return payload;
    }
}
