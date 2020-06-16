delete from user_role;
delete from user_table;

insert into user_table(id, username, password) values
(100, 'Alexey', '$2a$08$s4BotudZ3R36wY/hrjoHFunyV9bhZLERkSkgNDgeWHKltBnm4e50y'),
(200, 'Valery', '$2a$08$e8MNmnN1QxagVhk5eeYglOxZmOZ736UoXEi6i.iir0eHG6Yide9z2'),
(300, 'Alexandr', '$2a$08$7UU1MwezIj2SMG88Md2RzuIAMHMRox7BqYhSpt0L8yj3o2/fNcl3e'),
(400, 'Dmitry', '$2a$08$nknCJz1B9Qq9R6qb.ALbV.5RiU8D0oKZm/NdwtTLxlvdw7rM9YsWO');

insert into user_role(user_id, roles) values
(100, 'ADMIN'),
(200, 'USER'),
(300, 'USER'),
(400, 'USER');



