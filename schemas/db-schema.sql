create table strings(
    strings_id int not null primary key auto_increment,
    `value` varchar(100) not null
);

create table configuration_groups(
    configuration_groups_id int not null primary key auto_increment,
    name varchar(100) not null,
    key (name)
);

create table configuration_values(
    values_id int not null primary key auto_increment,
    configuration_groups_id int not null,
    `key` varchar(150) not null,
    `value` varchar(100) not null,
    key (configuration_groups_id),
    constraint configuration_groups_id_to_configuration_groups
    foreign key (configuration_groups_id)
    references configuration_groups(configuration_groups_id)
);


