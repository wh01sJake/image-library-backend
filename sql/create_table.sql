-- user table
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'userAccount',
    userPassword varchar(512)                           not null comment 'password',
    userName     varchar(256)                           null comment 'username',
    userAvatar   varchar(1024)                          null comment 'userAvatar',
    userProfile  varchar(512)                           null comment 'userProfile',
    userRole     varchar(256) default 'user'            not null comment 'role：user/admin',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment 'edit time',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete     tinyint      default 0                 not null comment '0 for not deleted, 1 for is deleted',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
) comment 'user' collate = utf8mb4_unicode_ci;
