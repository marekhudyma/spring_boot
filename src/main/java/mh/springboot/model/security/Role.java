package mh.springboot.model.security;

import mh.springboot.model.core.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Role extends AbstractEntity implements GrantedAuthority {

    @NotEmpty
    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = checkNotNull(authority);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, lastModified, authority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.created, other.created) &&
               Objects.equals(this.lastModified, other.lastModified) &&
               Objects.equals(this.authority, other.authority);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
