
CREATE TABLE "section"(
    id      BIGINT NOT NULL,
    title   VARCHAR UNIQUE NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE category(
    id          BIGINT NOT NULL,
    title       VARCHAR UNIQUE NOT NULL,
    section_id  INTEGER,

    PRIMARY KEY(id),
    FOREIGN KEY (section_id) REFERENCES "section"(id)
);

CREATE TABLE "parameter"(
    id         BIGINT NOT NULL,
    key        VARCHAR NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE auction(
    id            BIGINT NOT NULL,
    title         VARCHAR NOT NULL,
    description   TEXT NOT NULL,
    price         DOUBLE PRECISION NOT NULL,
    owner_id      INTEGER,
    category_id   INTEGER,


    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES "user"(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE auction_parameter(
    id              BIGINT NOT NULL,
    value           VARCHAR NOT NULL,
    auction_id      INTEGER,
    parameter_id    INTEGER,


    PRIMARY KEY (id),
    FOREIGN KEY (auction_id) REFERENCES auction(id),
    FOREIGN KEY (parameter_id) REFERENCES "parameter"(id)
);

CREATE TABLE auction_image(
    id          BIGINT NOT NULL,
    url         VARCHAR NOT NULL,
    auction_id  INTEGER,

    PRIMARY KEY (id),
    FOREIGN KEY (auction_id) REFERENCES auction(id)
);
