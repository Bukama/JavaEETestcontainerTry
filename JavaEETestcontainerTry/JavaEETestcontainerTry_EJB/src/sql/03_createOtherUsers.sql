
-- Third step: Creating synonyms
CREATE SYNONYM dept FOR c##schemauser.dept;
CREATE SYNONYM emp FOR c##schemauser.emp;


-- Forth step: Create user for application / test data and grant them rights
-- The prefix c## is definied by default in Oracle 12 and I'm to lazy to change it
CREATE USER c##readinguser IDENTIFIED BY oracle;

GRANT CONNECT TO c##readinguser;

GRANT SELECT, UPDATE ON emp TO c##readinguser;



CREATE USER c##writinguser IDENTIFIED BY oracle;

GRANT CONNECT TO c##writinguser;


GRANT SELECT, INSERT, UPDATE, DELETE ON dept TO c##writinguser;
GRANT SELECT, INSERT, UPDATE, DELETE ON emp TO c##writinguser;


commit;


-- FIXME: Synonyms are not visible / usable by users
