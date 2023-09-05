package br.edu.unifaj.view;

public interface View {

    class User {}

    class Workspace extends User {}


    class Project extends Workspace {}


    class Catalog extends Project {}

    class Card extends Catalog {}
}
