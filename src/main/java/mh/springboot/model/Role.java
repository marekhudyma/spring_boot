package mh.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractEntity implements GrantedAuthority {

    @NotEmpty
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }
}
