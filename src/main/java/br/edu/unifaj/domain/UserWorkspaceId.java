package br.edu.unifaj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserWorkspaceId implements Serializable {
    private static final long serialVersionUID = 3140867044683306239L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserWorkspaceId entity = (UserWorkspaceId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.workspaceId, entity.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, workspaceId);
    }

}