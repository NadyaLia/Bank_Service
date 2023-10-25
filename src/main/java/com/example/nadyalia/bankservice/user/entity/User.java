package com.example.nadyalia.bankservice.user.entity;

import com.example.nadyalia.bankservice.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank(message = "Username must be not blank")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can contain only latin letters and numbers")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password must be not blank")
//    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()]+$", message = "Password can contain only latin letters and numbers and " +
//            "some special characters")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private String type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

