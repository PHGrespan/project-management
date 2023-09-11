package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "user", schema = "project_management")
public class User {

    @JsonView(View.User.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @JsonView(View.User.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView(View.User.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Size(max = 255)
    @NotNull
    @Column(name = "pass", nullable = false)
    private String pass;

    @JsonView(View.User.class)
    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @JsonView(View.Workspace.class)
    @JsonProperty("workspaces")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserWorkspace> userWorkspaces = new ArrayList<>();

}