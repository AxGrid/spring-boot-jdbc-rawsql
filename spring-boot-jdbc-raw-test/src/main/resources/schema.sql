create table if not exists my_test_table (
    id bigint primary key auto_increment not null,
    `name` varchar(150),
    `enum` varchar(100),
    `data` longtext,
    `dval` double
)
