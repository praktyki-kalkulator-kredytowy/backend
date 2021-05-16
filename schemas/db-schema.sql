create table strings(
    strings_id int not null primary key auto_increment,
    `value` varchar(100) not null
);

create table configuration(
    configuration_id int not null primary key auto_increment,
    configuration_key varchar(150) not null,
    configuration_value varchar(100) not null,
    configuration_group varchar(100) not null,
    key (configuration_group)
);


