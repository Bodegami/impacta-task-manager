package br.com.bodegami.task_manager.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RoleName name;

    public void setName(RoleName roleName) {
        this.name = roleName;
    }
}
