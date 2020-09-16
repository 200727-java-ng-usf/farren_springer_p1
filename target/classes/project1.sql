-- project 1 db editor

CREATE USER revatureers_app
WITH PASSWORD 'revabank';

grant all privileges
on database postgres
to revabank_app;

drop table ers_reimbursements;
drop table ers_reimbursement_statuses;
drop table ers_reimbursement_types;
drop table ers_users;
drop table project1.ers_user_roles;

CREATE USER project1_app
WITH password 'revature';

GRANT ALL PRIVILEGES
ON database postgres
TO project1_app;

-- create table for types
create table ers_reimbursement_types(
	reimb_type_id 		serial,
	reimb_type 			varchar(10) UNIQUE
);

alter table ers_reimbursement_types add constraint types_pk
	PRIMARY KEY (reimb_type_id);

-- create table for roles
CREATE TABLE ers_user_roles(
	role_id 			serial,
	role_name 			varchar(10) UNIQUE
);

alter table ers_user_roles add constraint ers_user_roles_pk
	primary key (role_id);

-- create table for status
CREATE TABLE ers_reimbursement_statuses(
	reimb_status_id 	serial,
	reimb_status 		varchar(10) UNIQUE
);

alter table ers_reimbursement_statuses add constraint status_pk
	primary key (reimb_status_id);

-- create table for users
CREATE TABLE ers_users(
	ers_user_id			serial,
	username			varchar(25) UNIQUE,
	password			varchar(256),
	first_name			varchar(25),
	last_name			varchar(25),
	email				varchar(256) UNIQUE,
	user_role_id		int
);

alter table ers_users add constraint ers_users_pk
	primary key (ers_user_id);

alter table ers_users add constraint ers_user_roles_fk
	foreign key (user_role_id) references ers_user_roles;

-- create table for reimbursements
CREATE TABLE ers_reimbursements(
	reimb_id			serial,
	amount				numeric(6,2),
	submitted			timestamp,
	resolved			timestamp,
	description			text,
	receipt				text, -- blob or link
	author_id			int,
	resolver_id			int,
	reimb_status_id 	int,
	reimb_type_id		int
);

alter table ers_reimbursements add constraint ers_reimbursements_pk
	primary key (reimb_id);

alter table ers_reimbursements add constraint author_id_fk
	foreign key (author_id) references ers_users;

alter table ers_reimbursements add constraint resolver_id_fk
	foreign key (resolver_id) references ers_users;

alter table ers_reimbursements add constraint reimb_status_id_fk
	foreign key (reimb_status_id) references ers_reimbursement_statuses;

alter table ers_reimbursements add constraint reimb_type_id_fk
	foreign key (reimb_type_id) references ers_reimbursement_types;

-- create user role values
insert into ers_user_roles (role_name)
values ('Admin'), ('FinManager'), ('Employee'), ('Inactive');

-- make sure user roles are 1, 2, and 3
--update ers_user_roles 
--set role_id = 1
--where role_name = 'Admin';
--
--update ers_user_roles 
--set role_id = 2
--where role_name = 'FinManager';
--
--update ers_user_roles 
--set role_id = 3
--where role_name = 'Employee';
--
--update ers_user_roles 
--set role_id = 4
--where role_name = 'Inactive';

-- create ers_reimbursement_types values
insert into ers_reimbursement_types (reimb_type)
values ('Lodging'), ('Travel'), ('Food'), ('Other');

-- create ers_reimbursement_statuses values
insert into ers_reimbursement_statuses (reimb_status)
values ('Pending'), ('Approved'), ('Denied');

-- populate ers_users table with some dummy data
insert into ers_users (username, password, first_name, last_name, email, user_role_id)
values
	('aanderson', 'password', 'Alice', 'Anderson', 'aanderson@revature.com', 1),
	('bbailey', 'password', 'Bob', 'Bailey', 'bbailey@revature.com', 2),
	('ccalhoun', 'password', 'Charles', 'Calhoun', 'ccalhoun@revature.com', 3),
	('ddavis', 'password', 'Daniel', 'Davis', 'ddavis@revature.com', 3);

-- populate ers_reimbursements with some dummy data
insert into ers_reimbursements (amount, description, author_id, resolver_id, reimb_status_id, reimb_type_id)
values 
	(200, 'Marriot', 3, 2, 1, 1),
	(50, 'Olive Garden',  4, 2, 1, 3),
	(350, 'SouthWest Airlines', 3, 2, 1, 2);


-- more dummy ers_users
insert into ers_users (username, password, first_name, last_name, email, user_role_id)
values ('bgriffin', 'password', 'Brian', 'Griffin', 'bgriffin@gmail.com', 2);

-- convert to json notation
--SELECT ers_user_id
--	,json_agg(row_to_json((username, password, first_name, last_name, email, user_role_id))) AS JsonData
--FROM project1.ers_users
--GROUP BY ers_user_id;

--select reimb_id
--	,json_agg(row_to_json((amount, submitted, resolved, description, receipt, author_id, resolver_id, reimb_status_id, reimb_type_id))) as JsonData
--from project1.ers_reimbursements 
--group by reimb_id;

-- more dummy ers_users
insert into ers_users (username, password, first_name, last_name, email, user_role_id)
values ('jjonas', 'password', 'Joe', 'Jonas', 'jjonas@gmail.com', 1);

-- more dummy ers_users
insert into ers_users (username, password, first_name, last_name, email, user_role_id)
values ('kjonas', 'password', 'Kevin', 'Jonas', 'kjonas@gmail.com', 1);

SELECT * FROM project1.ers_reimbursements er 
            JOIN project1.ers_reimbursement_types rt 
            ON er.reimb_type_id = rt.reimb_type_id 
            JOIN project1.ers_reimbursement_statuses rs
            ON er.reimb_status_id = rs.reimb_status_id 
where author_id = 7;

--UPDATE project1.ers_reimbursements 
--SET amount = '400', 
--submitted = '2020-09-01 13:53:41', 
--description = 'asdfjkl', 
--reimb_type_id = '3' 
--WHERE reimb_id = '7'; 

select * from ers_reimbursements er 
where author_id = 11 and reimb_status_id = 2;

SELECT * FROM project1.ers_users eu 
JOIN project1.ers_user_roles ur 
ON eu.user_role_id = ur.role_id
where eu.user_role_id = 1;

SELECT * FROM project1.ers_reimbursements er 
JOIN project1.ers_reimbursement_types ert 
ON er.reimb_type_id = ert.reimb_type_id 
JOIN project1.ers_reimbursement_statuses ers 
ON er.reimb_status_id = ers.reimb_status_id
WHERE er.reimb_id = 19;

SELECT * FROM project1.ers_reimbursements er 
JOIN project1.ers_reimbursement_types ert 
ON er.reimb_type_id = ert.reimb_type_id 
JOIN project1.ers_reimbursement_statuses ers 
ON er.reimb_status_id = ers.reimb_status_id 
where er.reimb_id = 9;

SELECT * FROM project1.ers_reimbursements er 
JOIN project1.ers_reimbursement_types ert 
ON er.reimb_type_id = ert.reimb_type_id 
JOIN project1.ers_reimbursement_statuses ers 
ON er.reimb_status_id = ers.reimb_status_id 
order by er.reimb_status_id;

commit;

