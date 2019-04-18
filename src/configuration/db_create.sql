create
database contracts;
create user cqrs with encrypted password 'cqrs';
grant all privileges on database contracts to cqrs;
