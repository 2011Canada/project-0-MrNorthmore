set schema 'financial';

select * from users;
select * from accounts;
select * from employees;
select * from transactions;
select * from transfers;

insert into accounts (balance, user_id, account_approved)
	values (8900.00, 2, true);

insert into employees (user_id)
	values (1);
	
select * from accounts
	where user_id = 1;

insert into users (username, "password")
	values ('matt', 'matt');

update "accounts" as a set balance = 299 where account_num = 2;
	
select u.user_id, u.username, u."password", e.employee_id from users u
	inner join employees e on u.user_id = e.user_id and u."username" = 'matt' and u."password" = 'matt';

update "accounts"
	set account_approved = true 
	where account_num = 1;
	
select * from transfers
	where to_account = 1;

select * from accounts
	where user_id = 2;

delete from transfers
	where transfer_id = 1;
