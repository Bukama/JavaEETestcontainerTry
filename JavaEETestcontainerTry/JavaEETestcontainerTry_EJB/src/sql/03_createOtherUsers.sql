
-- Third step: Create user for application / test data and grant them rights

CREATE USER c##readinguser IDENTIFIED BY oracle;

GRANT CONNECT TO c##readinguser;

GRANT SELECT, INSERT, UPDATE on c##schemauser.emp TO c##readinguser;



CREATE USER c##writinguser IDENTIFIED BY oracle;

GRANT CONNECT TO c##writinguser;

GRANT SELECT, INSERT, UPDATE, DELETE on c##schemauser.emp TO c##writinguser;


commit;
