package co.edu.parcial.adapters.out.persistence.entity;

import co.edu.parcial.domain.model.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("users")
public class UserEntity implements Persistable<String> {
    @Id
    private String id;
    @Transient
    private boolean isNew;
    private String username;
    @Column("password_hash")
    private String passwordHash;
    private Role role;
    @Column("created_at")
    private Instant createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public void setNew(boolean aNew) { isNew = aNew; }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}

