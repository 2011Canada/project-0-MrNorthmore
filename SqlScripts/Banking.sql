-- commands for dropping and re-creating schema 
drop schema if exists financial cascade;
create schema financial;
set schema 'financial';

-- going to need the following tables: Users, Customers, Employees, Accounts
create table users(
	user_id serial primary key,
	username text unique not null,
	"password" text not null
);

create table accounts(
	account_num serial primary key,
	balance numeric(10,2),
	user_id int not null references "users"("user_id"),
	account_approved boolean not null
);

create table transactions(
	transaction_id serial primary key,
	account_num int not null references "accounts"("account_num"),
	operation text not null,
	date_time timestamp not null,
	amount numeric(10,2) not null
);

create table employees(
	employee_id serial not null primary key,
	user_id int not null references "users"("user_id")
);

create table transfers(
	transfer_id serial not null primary key,
	from_account int not null references "accounts"("account_num"),
	to_account int not null references "accounts"("account_num"),
	amount numeric(10,2) not null
);

insert into users (username, "password")
	values ('e', 'e');

insert into users (username, "password")
	values ('matt', 'matt');

insert into employees (user_id)
	values (1);


