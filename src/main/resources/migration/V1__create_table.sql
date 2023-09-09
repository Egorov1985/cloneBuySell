CREATE TABLE buysell.user
(
    id              BIGINT AUTO_INCREMENT,
    active          BIT           NOT NULL,
    date_of_created DATETIME(6)   NOT NULL,
    email           VARCHAR(255)  NOT NULL UNIQUE,
    name            VARCHAR(255)  NOT NULL,
    password        varchar(2048) NOT NULL,
    phone_number    varchar(255)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE buysell.product
(
    id              BIGINT AUTO_INCREMENT,
    city            VARCHAR(255) NOT NULL,
    date_of_created DATETIME(6)  NOT NULL,
    description     TEXT         NOT NULL,
    preview_image   LONGBLOB,
    price           BIGINT       NOT NULL,
    title           VARCHAR(50)  NOT NULL,
    user_id         BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE buysell.product_images_path_list
(
    product_id       BIGINT       NOT NULL,
    images_path_list VARCHAR(255) NULL
);

CREATE TABLE buysell.user_role
(
    user_id BIGINT       NOT NULL null,
    roles   VARCHAR(255) NULL
);
alter table product
    add constraint product_user_id
        foreign key (user_id) references buysell.user (id);

alter table user_role
    add constraint user_role_user_id
        foreign key (user_id) references user (id);

alter table product_images_path_list
    add constraint product_images_path_list_product_id
        foreign key (product_id) references product (id);
