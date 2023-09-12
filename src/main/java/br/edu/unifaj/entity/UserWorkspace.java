package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_workspace", schema = "project_management")
public class UserWorkspace {

    @JsonIgnore
    @EmbeddedId
    private UserWorkspaceId id;

    @JsonIgnore
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonView(View.Workspace.class)
    @MapsId("workspaceId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @JsonView(View.Workspace.class)
    @NotNull
    @Column(name = "owner", nullable = false)
    private Boolean owner = false;

}