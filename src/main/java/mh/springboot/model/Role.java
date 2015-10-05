package mh.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractEntity { //implements GrantedAuthority { TODO HUDYMA - REMOVE SECURITY

    @NotEmpty
    private String authority;

    //@Override
    //TODO HUDYMA - REMOVE SECURITY
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }
}
