package mh.springboot.model.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Column
    protected OffsetDateTime created;

    @NotNull
    @Column
    protected OffsetDateTime lastModified;

    @PrePersist
    @PreUpdate
    protected void preSave() {
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

}
