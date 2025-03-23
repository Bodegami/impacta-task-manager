package br.com.bodegami.task_manager.domain.entity;

import br.com.bodegami.task_manager.security.Role;
import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @Builder.Default
    private UUID id = Generators.timeBasedEpochGenerator().generate();
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String userId) {
        this.id = UUID.fromString(userId);
    }
}
