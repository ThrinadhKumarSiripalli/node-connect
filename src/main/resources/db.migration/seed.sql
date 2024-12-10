CREATE TABLE connection_group
(
    id              serial PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    is_active       SMALLINT     NOT NULL,
    created_at      bigint       NOT NULL,
    last_updated_at bigint       NOT NULL,
    CONSTRAINT idx_connection_group_name UNIQUE (name)
);

CREATE TABLE virtual_node
(
    id                         serial PRIMARY KEY,
    name                       VARCHAR(255) NOT NULL,
    reports_to_virtual_node_id bigint NULL,
    connection_group_id        bigint       NOT NULL,
    is_active                  SMALLINT     NOT NULL,
    created_at                 bigint       NOT NULL,
    last_updated_at            bigint       NOT NULL,
    CONSTRAINT idx_virtual_node_name UNIQUE (name)
);