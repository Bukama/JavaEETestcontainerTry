-- First step: Create schemauser

CREATE USER schemauser
  IDENTIFIED BY oracle;

GRANT CONNECT TO schemauser;
GRANT create session TO schemauser;
GRANT create table TO schemauser;
GRANT create view TO schemauser;
GRANT create any trigger TO schemauser;
GRANT create any procedure TO schemauser;
GRANT create sequence TO schemauser;
GRANT create synonym TO schemauser;


-- Second step: Create user for application / test data and grant them rights

CREATE USER readinguser IDENTIFIED BY oracle;

GRANT CONNECT TO readinguser;

GRANT SELECT, INSERT, UPDATE on schemauser.emp TO readinguser;



CREATE USER writinguser IDENTIFIED BY oracle;

GRANT CONNECT TO writinguser;

GRANT SELECT, INSERT, UPDATE, DELETE on schemauser.emp TO writinguser;