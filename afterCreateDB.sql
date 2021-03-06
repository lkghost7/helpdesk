create or replace function system_user_findbyid(p_id integer)
  returns refcursor
language plpgsql
as $$
DECLARE
  ref refcursor;
BEGIN
  OPEN ref FOR
  select su.id,
         su.name,
         su.family,
         su.e_mail,
         su.password,
         b.id     as b_id,
         b.name   as b_name,
         b.address as b_adress,
         s.id     as s_id,
         s.name   as s_name
  from system_users su,
       branches b,
       subdivisions s
  where su.branch_id = b.id
    and su.subdivision_id = s.id
    and su.id = p_id;
  RETURN ref;
END;
$$;

alter function system_user_findbyid(e_mail CHARACTER VARYING)
  owner to root;

create or replace function system_user_findby_email(s_email varchar(50))
  returns refcursor
language plpgsql
as $$
DECLARE
  ref refcursor;
BEGIN
  OPEN ref FOR
  select su.id,
         su.name,
         su.family,
         su.e_mail,
         su.password,
         b.id     as b_id,
         b.name   as b_name,
         b.address as b_adress,
         s.id     as s_id,
         s.name   as s_name
  from system_users su,
       branches b,
       subdivisions s
  where su.branch_id = b.id
    and su.subdivision_id = s.id
    and su.e_mail = s_email;
  RETURN ref;
END;
$$;

alter function system_user_findby_email(CHARACTER VARYING)
  owner to root;