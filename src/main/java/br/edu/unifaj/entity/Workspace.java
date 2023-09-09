package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "workspace", schema = "project_management")
public class Workspace {

    @JsonView(View.Workspace.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id", nullable = false)
    private Long id;

    @JsonView(View.Workspace.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView(View.Workspace.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @JsonView(View.Workspace.class)
    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @JsonView(View.Workspace.class)
    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @JsonView(View.Project.class)
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserWorkspace> userWorkspaces = new ArrayList<>();

}