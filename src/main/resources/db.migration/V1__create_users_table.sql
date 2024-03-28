CREATE TABLE users(
                      id UUID primary key,
                      email varchar(30) not null,
                      password varchar(30) not null,
                      nickname varchar(30) not null,
                      name varchar(50) not null,
                      role varchar(50) not null
);

INSERT INTO users (id, email, password, nickname, name, role)
VALUES
    ('9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik1@example.com', '123456', 'denchik', 'John John', 'ROLE_ADMIN'),
    ('4c6a8dda-f4f2-4981-8a01-c314115828f6', 'rus@russia.ru', 'rusrus', 'Bronislav', 'Margo Black', 'ROLE_USER'),
    ('8513be2f-c6bd-4ffd-83ef-d03773756e53', 'yascher@snake.com', '122111133', 'Cobron', 'Goblin Grey', 'ROLE_USER');
