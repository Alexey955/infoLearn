delete from user_role;
delete from user_table;

insert into user_table(id, username, password) values
(1, 'Alexey', '111111'),
(2, 'Valery', '222222'),
(3, 'Alexandr', '333333'),
(4, 'Dmitry', '333333');

insert into user_role(user_id, roles) values
(1, 'ADMIN'),
(2, 'USER'),
(3, 'USER'),
(4, 'USER');


