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
    `date` datetime
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
