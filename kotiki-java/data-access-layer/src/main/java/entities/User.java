package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import entities.Role;
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private Long ownerId;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Role> roles;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return ownerId;
    }

    public void setOwner(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        role.addUser(this);
        roles.add(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
