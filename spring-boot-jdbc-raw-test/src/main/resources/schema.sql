DROP TABLE IF EXISTS my_table;
CREATE TABLE IF NOT EXISTS my_table(
    `id` bigint primary key auto_increment not null,
    `name`  varchar(150),
    `age`   int,
    `enum1` varchar(100),
    `enum2` int,
    `data`  longtext,
    `dval`  double,
    `longDate` bigint,
    `stringDate` varchar(100),
    `date` datetime,
    `platform` varchar(100),
    `platform_int` int
);

/*
    `name`  varchar(150),
    `age`   int,
    `enum1` varchar(100),
    `enum2` int,
    `data`  longtext,
    `dval`  double,
    `longDate` bigint,
    `stringDate` varchar(100)
*/
