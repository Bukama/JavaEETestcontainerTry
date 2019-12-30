-- First step: Create schemauser

CREATE USER c##schemauser
  IDENTIFIED BY oracle;

GRANT CONNECT TO c##schemauser;
GRANT create session TO c##schemauser;
GRANT create table TO c##schemauser;
GRANT create view TO c##schemauser;
GRANT create any trigger TO c##schemauser;
GRANT create any procedure TO c##schemauser;
GRANT create sequence TO c##schemauser;
GRANT create synonym TO c##schemauser;

GRANT UNLIMITED TABLESPACE TO c##schemauser;


commit;
