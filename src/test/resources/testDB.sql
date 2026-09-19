DROP TABLE IF EXISTS trackinplaylist;
DROP TABLE IF EXISTS track;
DROP TABLE IF EXISTS playlist;
DROP TABLE IF EXISTS user;

create table track
(
    id               int          not null
        primary key,
    title            varchar(100) not null,
    performer        varchar(100) not null,
    duration         int          not null,
    album            varchar(100) null,
    playcount        int          null,
    publication_date varchar(20)  null,
    description      varchar(255) null
);

create table user
(
    username  varchar(50)  not null
        primary key,
    password  varchar(255) not null,
    full_name varchar(50)  not null,
    token     varchar(255) null,
    constraint token_UNIQUE
        unique (token)
);

create table playlist
(
    id    int          not null
        primary key,
    name  varchar(255) not null,
    owner varchar(50)  not null,
    constraint fk_playlistOwner_user_Fullname
        foreign key (owner) references user (username)
            on update cascade on delete cascade
);

create index fk_playlistOwner_user_Fullname_idx
    on playlist (owner);

create table trackinplaylist
(
    trackId           int     not null,
    playlistId        int     not null,
    offline_available tinyint null,
    primary key (trackId, playlistId),
    constraint fk_trackinplaylistPlaylistId_playlistId
        foreign key (playlistId) references playlist (id)
            on update cascade on delete cascade,
    constraint fk_trackinplaylistTrackId_trackId
        foreign key (trackId) references track (id)
            on update cascade on delete cascade
);

create index fk_trackinplaylistPlaylistId_playlistId_idx
    on trackinplaylist (playlistId);

INSERT INTO track (id, title, performer, duration, album, playcount, publication_date, description)
VALUES (1, 'Bohemian Rhapsody', 'Queen', 354, 'A Night at the Opera', 10000, '1975-10-31', 'Iconic rock song by Queen');
INSERT INTO track (id, title, performer, duration, album, playcount, publication_date, description)
VALUES (2, 'Stairway to Heaven', 'Led Zeppelin', 480, null, null, null, null);
INSERT INTO track (id, title, performer, duration, album, playcount, publication_date, description)
VALUES (3, 'Hotel California', 'Eagles', 390, null, null, null, null);

INSERT INTO user (username, password, full_name, token)
VALUES ('Gino', '$2a$10$Zr6VBqeOK2eRsKs8pzJeteGWnG9YljKx/1WoulIKmPaSOvxPt25g.', 'Gino Janssen',
        'b34797ea-7977-40dd-af91-794397843e65');
INSERT INTO user (username, password, full_name, token)
VALUES ('NotGino', '$2a$10$Zr6VBqeOK2eRsKs8pzJeteGWnG9YljKx/1WoulIKmPaSOvxPt25g.', 'Not Gino',
        '73ac7516-a7a8-4d1a-83c4-3ccc44868224');

INSERT INTO playlist (id, name, owner)
VALUES (1, 'Best playlist', 'Gino');
INSERT INTO playlist (id, name, owner)
VALUES (2, 'Not My Playlist', 'NotGino');

INSERT INTO trackinplaylist (trackId, playlistId, offline_available)
VALUES (1, 1, null);
INSERT INTO trackinplaylist (trackId, playlistId, offline_available)
VALUES (1, 2, null);
INSERT INTO trackinplaylist (trackId, playlistId, offline_available)
VALUES (2, 2, null);
INSERT INTO trackinplaylist (trackId, playlistId, offline_available)
VALUES (3, 1, null);
INSERT INTO trackinplaylist (trackId, playlistId, offline_available)
VALUES (3, 2, null);