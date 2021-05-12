create table strings(
    strings_id int not null primary key auto_increment,
    `value` varchar(100) not null
);
create table configuration_groups(
    groups_id int not null primary key auto_increment,
    group_name varchar(100) not null
);
create table configuration_values(
    values_id int not null primary key auto_increment,
    `key` varchar(150) not null,
    `value` varchar(100) not null
);
create table groups_to_values(
    groups_id int not null,
    values_id int not null,
    key(groups_id),
    key(values_id),
    constraint connect_groups_id foreign key (groups_id) references configuration_groups(groups_id),
    constraint connect_values_id foreign key (values_id) references configuration_values(values_id)
);


