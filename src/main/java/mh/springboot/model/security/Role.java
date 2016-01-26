package mh.springboot.model.security;

import mh.springboot.model.core.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

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
}
