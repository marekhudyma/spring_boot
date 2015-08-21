package mh.springboot.model;

import java.time.OffsetDateTime;

public interface AbstractEntity {

    Long getId();

    OffsetDateTime getCreated();

    OffsetDateTime getLastModified();

}
