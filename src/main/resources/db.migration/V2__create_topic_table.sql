CREATE TABLE topic(
                      id UUID primary key,
                      name varchar(100) not null,
                      user_creator_id UUID REFERENCES users(id),
                      user_creator_nickname varchar(100) not null,
                      creation_date timestamp not null
);

INSERT INTO topic (id, name, user_creator_id, user_creator_nickname, creation_date)
VALUES
    ('9e40b5f3-39c2-408c-97a8-03b4ca64d671', 'Topic for 1.1.1',                     '4c6a8dda-f4f2-4981-8a01-c314115828f6', 'Bronislav',     '2024-02-05 09:00:00'),
    ('f77ec6e2-b02d-454d-abc8-8cca147acb7c', 'Мега вброс',                          '4c6a8dda-f4f2-4981-8a01-c314115828f6', 'Bronislav',     '2024-02-22 09:00:00'),
    ('3596ee2c-59d8-4c0c-ac31-a70953800ea0', 'Обидная рифма на имя Эдик',           '9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik',       '2024-02-08 09:00:00'),
    ('1d14a6e7-a3bc-426c-bfd0-51f8bb846024', 'Топ 10 анекдотов',                    '8513be2f-c6bd-4ffd-83ef-d03773756e53', 'Cobron',        '2024-02-01 09:00:00'),
    ('7f41b9cc-f176-4d17-8f24-f2f3f9c5d116', 'Funny topic',                         '9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik',       '2024-02-10 09:00:00'),
    ('6efc4424-ffcb-4cf5-84f1-4d78dfac6eb5', 'Interesting discussion',              '4c6a8dda-f4f2-4981-8a01-c314115828f6', 'Bronislav',     '2024-02-15 09:00:00'),
    ('785b217e-32ae-40e0-b6b5-96a67435cf57', 'Exciting topic',                      '9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik',       '2024-02-20 09:00:00'),
    ('dbbf8a89-0fd1-4c87-b1ae-84724d845775', 'Hot discussion',                      '8513be2f-c6bd-4ffd-83ef-d03773756e53', 'Cobron',        '2024-02-25 09:00:00'),
    ('b8e4f857-b3eb-4e68-b4a7-3b4ba50f6cc0', 'Controversial topic',                 '4c6a8dda-f4f2-4981-8a01-c314115828f6', 'Bronislav',     '2024-03-01 09:00:00'),
    ('37f6f234-3786-49ad-8603-057a148b85da', 'Topic for debate',                    '9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik',       '2024-03-05 09:00:00'),
    ('f2ec2684-b8c8-41f7-aa44-cf28e71771b7', 'New topic',                           '8513be2f-c6bd-4ffd-83ef-d03773756e53', 'Cobron',        '2024-03-10 09:00:00'),
    ('4c6a95eb-7429-4be7-b460-74ab9f07ee58', 'Exciting discussion',                 '4c6a8dda-f4f2-4981-8a01-c314115828f6', 'Bronislav',     '2024-03-15 09:00:00'),
    ('9453e34c-6e0f-4343-8bf1-422b6a9f4de1', 'Fun chat',                            '8513be2f-c6bd-4ffd-83ef-d03773756e53', 'Cobron',        '2024-03-20 09:00:00'),
    ('8513bf26-3f7a-4574-b2fd-164e83238c47', 'Discussion about current events',     '9453e047-a56b-42f6-95a5-328b8a22841e', 'denchik',       '2024-03-25 09:00:00');