CREATE USER readinguser IDENTIFIED BY oracle;

GRANT CONNECT TO readinguser;

GRANT SELECT, INSERT, UPDATE on bish.emp TO readinguser;



CREATE USER writingguser IDENTIFIED BY oracle;

GRANT CONNECT TO writingguser;

GRANT SELECT, INSERT, UPDATE, DELETE on bish.emp TO writingguser;

GRANT CREATE ANY TABLE to writingguser;
