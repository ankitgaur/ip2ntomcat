alter table vmdata.dbo.roles add constraint uq_roles_name unique (name)

alter table vmdata.dbo.users add constraint uq_users_username unique (user_name)