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
CREATE TABLE ers_reimbursement_types(
	reimb_type_id 		serial,
	reimb_type 			varchar(10) UNIQUE,
	
	CONSTRAINT types_pk
	PRIMARY KEY (reimb_type_id)
);

-- create table for roles
CREATE TABLE ers_user_roles(
	role_id 			serial,
	role_name 			varchar(10) UNIQUE,
	
	CONSTRAINT ers_user_roles_pk
	PRIMARY KEY (role_id)
);

-- create table for status
CREATE TABLE ers_reimbursement_statuses(
	reimb_status_id 	serial,
	reimb_status 		varchar(10) UNIQUE,
	
	CONSTRAINT status_pk
	PRIMARY KEY (reimb_status_id)
);

-- create table for users
CREATE TABLE ers_users(
	ers_user_id			serial,
	username			varchar(25) UNIQUE,
	password			varchar(256),
	first_name			varchar(25),
	last_name			varchar(25),
	email				varchar(256) UNIQUE,
	user_role_id		int,
	
	CONSTRAINT ers_users_pk
	PRIMARY KEY (ers_user_id),
	
	CONSTRAINT ers_user_roles_fk
	FOREIGN KEY (user_role_id)
	REFERENCES ers_user_roles
);

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
	reimb_type_id		int,
	
	CONSTRAINT ers_reimbursements_pk
	PRIMARY KEY (reimb_id),
	
	CONSTRAINT author_id_fk
	FOREIGN KEY (author_id)
	REFERENCES ers_users,
	
	CONSTRAINT resolver_id_fk
	FOREIGN KEY (resolver_id)
	REFERENCES ers_users,
	
	constraint reimb_status_id_fk
	foreign key (reimb_status_id)
	references ers_reimbursement_statuses,
	
	constraint reimb_type_id_fk
	foreign key (reimb_type_id)
	references ers_reimbursement_types
);

-- create user role values
insert into ers_user_roles (role_name)
values ('Admin'), ('FinManager'), ('Employee');

-- make sure user roles are 1, 2, and 3
update ers_user_roles 
set role_id = 1
where role_name = 'Admin';

update ers_user_roles 
set role_id = 2
where role_name = 'FinManager';

update ers_user_roles 
set role_id = 3
where role_name = 'Employee';

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
insert into ers_reimbursements (amount, description, receipt, author_id, resolver_id, reimb_status_id, reimb_type_id)
values 
	(200, 'Marriot', 'receipt text here', 7, 6, 1, 1),
	(50, 'Olive Garden', 'more receipt text here', 8, 6, 1, 3),
	(350, 'SouthWest Airlines', 'even more receipt text here', 8, 6, 1, 2);


-- more dummy ers_users
insert into ers_users (username, password, first_name, last_name, email, user_role_id)
values ('bgriffin', 'password', 'Brian', 'Griffin', 'bgriffin@gmail.com', 2);

-- convert to json notation
SELECT ers_user_id
	,json_agg(row_to_json((username, password, first_name, last_name, email, user_role_id))) AS JsonData
FROM project1.ers_users
GROUP BY ers_user_id;

select reimb_id
	,json_agg(row_to_json((amount, submitted, resolved, description, receipt, author_id, resolver_id, reimb_status_id, reimb_type_id))) as JsonData
from project1.ers_reimbursements 
group by reimb_id;

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

-- create user role values
insert into project1.ers_user_roles (role_name)
values ('Inactive');

commit;

