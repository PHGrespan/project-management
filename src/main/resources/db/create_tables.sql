DROP DATABASE project_management;
CREATE DATABASE project_management;
USE project_management;

CREATE TABLE user
(
    user_id       SERIAL,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    pass          VARCHAR(255) NOT NULL,

    creation_date DATETIME     NOT NULL,

    PRIMARY KEY (user_id)
);

CREATE TABLE workspace
(
    workspace_id  SERIAL,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,

    creation_date DATETIME     NOT NULL,
    update_date   DATETIME     NOT NULL,

    PRIMARY KEY (workspace_id)
);

CREATE TABLE project
(
    project_id         SERIAL,
    name               VARCHAR(255) NOT NULL,
    description        VARCHAR(255) NOT NULL,

    workspace_id       BIGINT UNSIGNED,
    workspace_position INT          NOT NULL,
    CONSTRAINT workspace_position UNIQUE (workspace_id, workspace_position),

    PRIMARY KEY (project_id),
    FOREIGN KEY (workspace_id) REFERENCES workspace (workspace_id)
);

CREATE TABLE catalog
(
    catalog_id       SERIAL,
    name             VARCHAR(255) NOT NULL,

    project_id       BIGINT UNSIGNED,
    project_position INT          NOT NULL,
    CONSTRAINT project_position UNIQUE (project_id, project_position),

    PRIMARY KEY (catalog_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id)
);

CREATE TABLE card
(
    card_id          SERIAL,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,

    catalog_id       BIGINT UNSIGNED,
    catalog_position INT          NOT NULL,
    CONSTRAINT catalog_position UNIQUE (catalog_id, catalog_position),

    PRIMARY KEY (card_id),
    FOREIGN KEY (catalog_id) REFERENCES catalog (catalog_id)
);

CREATE TABLE user_workspace
(
    user_id      BIGINT UNSIGNED,
    workspace_id BIGINT UNSIGNED,
    owner        boolean NOT NULL,

    PRIMARY KEY (user_id, workspace_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (workspace_id) REFERENCES workspace (workspace_id)
);