#############################cinema_video#################################
create table cinema_video
(
    video_id binary(16) not null,
    video_name varchar(255) null,
    is_hot tinyint default 0 null comment '是否热播',
    video_score_integer tinyint default 0 null,
    video_score_decimal tinyint default 0 null,
    video_region char(2) COLLATE utf8mb4_general_ci DEFAULT NULL,
    create_time DATETIME default now() null,
    update_time DATETIME default now() null,
    is_delete tinyint default 0 null,
    hits int default 0 null comment '点击量',
    source_url varchar(50) null comment '源地址',
    video_describe varchar(2000) null,
    play_address JSON null,
    nick_name JSON null,
    CONSTRAINT `video_region_chk_` CHECK (((`video_region` = _utf8mb4'US') or (`video_region` = _utf8mb4'UK') or
                                           (`video_region` = _utf8mb4'CN') or (`video_region` = _utf8mb4'HK') or
                                           (`video_region` = _utf8mb4'TW') or (`video_region` = _utf8mb4'JP') or
                                           (`video_region` = _utf8mb4'DE') or (`video_region` = _utf8mb4'FR') or
                                           (`video_region` = _utf8mb4'IT') or (`video_region` = _utf8mb4'ES') or
                                           (`video_region` = _utf8mb4'IN') or (`video_region` = _utf8mb4'TH') or
                                           (`video_region` = _utf8mb4'RS') or (`video_region` = _utf8mb4'CA') or
                                           (`video_region` = _utf8mb4'IR') or (`video_region` = _utf8mb4'AU') or
                                           (`video_region` = _utf8mb4'IE') or (`video_region` = _utf8mb4'SE') or
                                           (`video_region` = _utf8mb4'BR') or (`video_region` = _utf8mb4'DK') or
                                           (`video_region` = _utf8mb4'KR') or (`video_region` = _utf8mb4'FR')))
) ENGINE=InnoDB;

create unique index cinema_video_video_id_uindex
    on cinema_video (video_id);

alter table cinema_video
    add constraint cinema_video_pk
        primary key (video_id);

#############################cinema_tag#################################
create table cinema_tag
(
    id int,
    tag tinyint default 0 null comment '0 未分类 1 类型 2 年份 3 导演 4 演员 5 清晰度 6 语言',
    tag_name varchar(20) null
);

create unique index cinema_tag_id_uindex
    on cinema_tag (id);

alter table cinema_tag
    add constraint cinema_tag_pk
        primary key (id);

alter table cinema_tag modify id int auto_increment;

#############################cinema_video_tag#################################
create table cinema_video_tag
(
    id int,
    video_id binary(16) null,
    tag_id int null
);

create unique index cinema_video_tag_id_uindex
    on cinema_video_tag (id);

alter table cinema_video_tag
    add constraint cinema_video_tag_pk
        primary key (id);

alter table cinema_video_tag modify id int auto_increment;

#############################crawler_config#################################
create table crawler_config
(
    id int,
    crawler_name nvarchar(10) null,
    crawler_index_url nvarchar(40) null,
    crawler_status tinyint null comment '0：停用 1：启用'
);

INSERT INTO `tcinemadev1`.`crawler_config` (`crawler_name`, `crawler_index_url`, `crawler_status`) VALUES ('努努影院', 'https://www.nunuyy1.org/', '1');

create unique index crawler_config_id_uindex
    on crawler_config (id);

alter table crawler_config
    add constraint crawler_config_pk
        primary key (id);

alter table crawler_config modify id int auto_increment;

#############################crawler_config_detail#################################
create table crawler_config_detail
(
    id int,
    config_id int null,
    config_type_id int null,
    config_url nvarchar(50) null,
    config_xpath nvarchar(50) null,
    config_suffix nvarchar(30) null,
    config_attribute varchar(10) null,
    config_regex varchar(30) null,
    create_time DATETIME default now() null,
    update_time DATETIME default now() null,
    is_delete tinyint default 0 null
);

create unique index crawler_config_detail_id_uindex
    on crawler_config_detail (id);

alter table crawler_config_detail
    add constraint crawler_config_detail_pk
        primary key (id);

alter table crawler_config_detail modify id int auto_increment;

INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_url`, `config_xpath`, `config_suffix`) VALUES (1, 1, 'https://www.nunuyy1.org/dianying', '/html/body/div[1]/div/div[2]/ul/li', '/index_%d.html');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_url`, `config_xpath`, `config_suffix`) VALUES (1, 2, 'https://www.nunuyy1.org/dianshiju', '/html/body/div[1]/div/div[2]/ul/li', '/index_%d.html');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_url`, `config_xpath`, `config_suffix`) VALUES (1, 3, 'https://www.nunuyy1.org/zongyi', '/html/body/div[1]/div/div[2]/ul/li', '/index_%d.html');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_url`, `config_xpath`, `config_suffix`) VALUES (1, 4, 'https://www.nunuyy1.org/dongman', '/html/body/div[1]/div/div[2]/ul/li', '/index_%d.html');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_xpath`, `config_attribute`) VALUES (1, 5, '/a/div[1]/span', 'innerText');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_xpath`, `config_attribute`) VALUES (1, 6, '/a', 'href');
INSERT INTO `tcinemadev1`.`crawler_config_detail` (`config_id`, `config_type_id`, `config_url`, `config_xpath`, `config_suffix`, `config_attribute`,`config_regex`) VALUES (1, 7, '', '/html/body/div[1]/div/div[3]/ul/li', '/a', 'href','\\d+');


#############################crawler_config_type#################################
create table crawler_config_type
(
    id int,
    type_name nvarchar(10) null
);

create unique index crawler_config_type_id_uindex
    on crawler_config_type (id);

alter table crawler_config_type
    add constraint crawler_config_type_pk
        primary key (id);

alter table crawler_config_type modify id int auto_increment;

INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('电影页');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('电视剧页');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('综艺页');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('动漫页');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('角标');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('视频详情页');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('总页数');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('电影名称');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('主图');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('年份');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('评分');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('导演');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('主演');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('类型');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('国家');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('别名');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('剧情简介');
INSERT INTO `tcinemadev1`.`crawler_config_type` (`type_name`) VALUES ('链接');





