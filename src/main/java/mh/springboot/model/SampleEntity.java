package mh.springboot.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "SAMPLE_ENTITY")
public class SampleEntity extends AbstractEntity {

    @Column(name = "uuid", unique = true)
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}
