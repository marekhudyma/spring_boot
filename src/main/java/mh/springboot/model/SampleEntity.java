package mh.springboot.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "SAMPLE_ENTITY")
public class SampleEntity implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private OffsetDateTime created;

    @Column
    private OffsetDateTime lastModified;

    @Column(name = "uuid", unique = true)
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Column
    private String name;

    @PrePersist
    @PreUpdate
    private void preSave() {
        OffsetDateTime now = OffsetDateTime.now();
        if(created == null) {
            created = now;
        }
        lastModified = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(final OffsetDateTime created) {
        this.created = created;
    }

    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(final OffsetDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }
}
