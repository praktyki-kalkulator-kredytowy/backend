create table strings(
    strings_id int not null primary key auto_increment,
    `value` varchar(100) not null
);

create table configuration(
    configuration_id int not null primary key auto_increment,
    `key` varchar(150) not null,
    `value` varchar(100) not null,
    `group` varchar(100) not null,
    key (`group`)
);


