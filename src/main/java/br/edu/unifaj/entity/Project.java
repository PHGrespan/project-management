package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "project", schema = "project_management")
public class Project {

    @JsonView(View.Project.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long id;

    @JsonView(View.Project.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView(View.Project.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @JsonView(View.Project.class)
    @NotNull
    @Column(name = "workspace_position", nullable = false)
    private Integer workspacePosition;

    @JsonView(View.Catalog.class)
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Catalog> catalogs = new LinkedHashSet<>();

}