CREATE DATABASE project_management;
USE project_management;

CREATE TABLE user
(
    id_user      int          NOT NULL AUTO_INCREMENT,
    name         varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    pass         varchar(255) NOT NULL,

    creationDate DATETIME     NOT NULL,

    PRIMARY KEY (id_user)
);

CREATE TABLE workspace
(
    id_workspace int          NOT NULL AUTO_INCREMENT,
    name         varchar(255) NOT NULL,
    description  varchar(255) NOT NULL,

    creationDate DATETIME     NOT NULL,
    updateDate   DATETIME     NOT NULL,

    PRIMARY KEY (id_workspace)
);

CREATE TABLE project
(
    id_project         int          NOT NULL AUTO_INCREMENT,
    name               varchar(255) NOT NULL,
    description        varchar(255) NOT NULL,

    id_workspace       int          NOT NULL,
    workspace_position int          NOT NULL,

    PRIMARY KEY (id_project),
    FOREIGN KEY (id_workspace) REFERENCES workspace (id_workspace)
);

CREATE TABLE list
(
    id_list          int          NOT NULL AUTO_INCREMENT,
    name             varchar(255) NOT NULL,

    id_project       int          NOT NULL,
    project_position int          NOT NULL,

    PRIMARY KEY (id_list),
    FOREIGN KEY (id_project) REFERENCES project (id_project)
);

CREATE TABLE card
(
    id_card       int          NOT NULL AUTO_INCREMENT,
    name          varchar(255) NOT NULL,
    description   varchar(255) NOT NULL,

    id_list       int          NOT NULL,
    list_position int          NOT NULL,

    PRIMARY KEY (id_card),
    FOREIGN KEY (id_list) REFERENCES list (id_list)
);

CREATE TABLE user_workspace
(
    id_user      int     NOT NULL,
    id_workspace int     NOT NULL,
    owner        boolean NOT NULL,

    PRIMARY KEY (id_user, id_workspace),
    FOREIGN KEY (id_user) REFERENCES user (id_user),
    FOREIGN KEY (id_workspace) REFERENCES workspace (id_workspace)
);