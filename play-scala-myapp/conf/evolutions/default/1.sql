# --- !Ups
create table people (
    id int auto_increment primary key,
    name varchar(255) not null,
    mail varchar(255) not null,
    tel varchar(255)
);
insert into people values (default, 'taro', 'taro@yamada', '999-999');
insert into people values (default, 'hanako', 'hanako@yamada', '888-888');
insert into people values (default, 'sachiko', 'sachiko@yamada', '777-777');

# --- !Downs
drop table people