package mh.springboot.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import mh.springboot.model.core.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="USER_ACCOUNT")
public class User extends AbstractEntity implements UserDetails {

    /**
     * for example:
     * FACEBOOK_10210381981735602
     * GOOGLE_109591818087491991216
     */
    private String externalId;

    @NotEmpty
    private String name;

    @Column(unique = true, nullable = true)
    private String login;

    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String externalId, String login, String password, String name) {
        this(externalId, login, password, name, ImmutableSet.of());
    }

    public User(String externalId, String login, String password, String name, Set<Role> roles) {
        this.externalId = externalId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    //-----------------------

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    //-----------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, lastModified, externalId, name, login, password, roles);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.created, other.created) &&
               Objects.equals(this.lastModified, other.lastModified) &&
               Objects.equals(this.externalId, other.externalId) &&
               Objects.equals(this.name, other.name) &&
               Objects.equals(this.login, other.login) &&
               Objects.equals(this.password, other.password) &&
               Objects.equals(this.roles, other.roles);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}

