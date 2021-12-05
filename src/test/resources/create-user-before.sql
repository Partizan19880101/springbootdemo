delete from user_role;
delete from usr;

insert into usr(id, active, address, email, name, password, phone, username) values
(1, true, '123', 'testMail1@mail.com', 'testName1', '123', 'testPhone1', 'testu1'),
(3, true, '123', 'testMail2@mail.com', 'testName2', '123', 'testPhone2', 'testu2');

insert into user_role(user_id, roles) values
(1, 'USER');