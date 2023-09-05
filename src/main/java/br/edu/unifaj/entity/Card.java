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

@Getter
@Setter
@Entity
@Table(name = "card", schema = "project_management")
public class Card {

    @JsonView(View.Card.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    @JsonView(View.Card.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView(View.Card.class)
    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @JsonView(View.Card.class)
    @NotNull
    @Column(name = "catalog_position", nullable = false)
    private Integer catalogPosition;

}