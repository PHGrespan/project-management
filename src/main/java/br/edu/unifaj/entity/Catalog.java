package br.edu.unifaj.entity;

import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "catalog", schema = "project_management")
public class Catalog {


    @JsonView(View.Catalog.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id", nullable = false)
    private Long id;

    @JsonView(View.Catalog.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonView(View.Catalog.class)
    @NotNull
    @Column(name = "project_position", nullable = false)
    private Integer projectPosition;

    @JsonView(View.Card.class)
    @OneToMany(mappedBy = "catalog", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

}