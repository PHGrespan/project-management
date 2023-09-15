package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @JsonView(View.Workspace.class)
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @JsonView(View.Project.class)
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserWorkspace> userWorkspaces = new HashSet<>();

}