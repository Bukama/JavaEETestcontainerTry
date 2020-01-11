-- First step: Create schemauser
-- The prefix c## is definied by default in Oracle 12 and I'm to lazy to change it

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


-- Second step: Create tables
-- (C)opyright  by Oracle
-- https://livesql.oracle.com/apex/livesql/file/content_O5AEB2HE08PYEPTGCFLZU9YCV.html

create table c##schemauser.dept(
  deptno     number(2,0),  
  dname      varchar2(14),  
  loc        varchar2(13),  
  constraint pk_dept primary key (deptno)  
);



create table c##schemauser.emp(  
  empno    number(4,0),  
  ename    varchar2(10),  
  job      varchar2(9),  
  mgr      number(4,0),  
  hiredate date,  
  sal      number(7,2),  
  comm     number(7,2),  
  deptno   number(2,0),  
  constraint pk_emp primary key (empno),  
  constraint fk_deptno foreign key (deptno) references dept (deptno)  
);


insert into c##schemauser.DEPT (DEPTNO, DNAME, LOC)
values(10, 'ACCOUNTING', 'NEW YORK');

insert into c##schemauser.dept  
values(20, 'RESEARCH', 'DALLAS');

insert into c##schemauser.dept  
values(30, 'SALES', 'CHICAGO');

insert into c##schemauser.dept  
values(40, 'OPERATIONS', 'BOSTON');


insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7839','KING','PRESIDENT',null,to_date('17.11.81','DD.MM.RR'),'5000',null,'10');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7698','BLAKE','MANAGER','7839',to_date('01.05.81','DD.MM.RR'),'2850',null,'30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7782','CLARK','MANAGER','7839',to_date('09.06.81','DD.MM.RR'),'2450',null,'10');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7566','JONES','MANAGER','7839',to_date('02.04.81','DD.MM.RR'),'2975',null,'20');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7788','SCOTT','ANALYST','7566',to_date('09.12.82','DD.MM.RR'),'3000',null,'20');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7902','FORD','ANALYST','7566',to_date('03.12.81','DD.MM.RR'),'3000',null,'20');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7369','SMITH','CLERK','7902',to_date('17.12.80','DD.MM.RR'),'800',null,'20');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7499','ALLEN','SALESMAN','7698',to_date('20.02.81','DD.MM.RR'),'1600','300','30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7521','WARD','SALESMAN','7698',to_date('22.02.81','DD.MM.RR'),'1250','500','30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7654','MARTIN','SALESMAN','7698',to_date('28.09.81','DD.MM.RR'),'1250','1400','30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7844','TURNER','SALESMAN','7698',to_date('08.09.81','DD.MM.RR'),'1500','0','30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7876','ADAMS','CLERK','7788',to_date('12.01.83','DD.MM.RR'),'1100',null,'20');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7900','JAMES','CLERK','7698',to_date('03.12.81','DD.MM.RR'),'950',null,'30');
insert into c##schemauser.EMP (EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO) values ('7934','MILLER','CLERK','7782',to_date('23.01.82','DD.MM.RR'),'1300',null,'10');

-- Third step:
-- Create user for application / test data
-- Create synonyms for tables
-- Grant rights for users


-- Reading user with rights to select and update emp
CREATE USER c##readinguser IDENTIFIED BY oracle;

CREATE SYNONYM c##readinguser.emp FOR c##schemauser.emp;

GRANT CONNECT TO c##readinguser;
GRANT SELECT, UPDATE ON c##readinguser.emp TO c##readinguser;


-- Writing user with rights to manipulate dept and emp
CREATE USER c##writinguser IDENTIFIED BY oracle;

CREATE SYNONYM c##writinguser.dept FOR c##schemauser.dept;
CREATE SYNONYM c##writinguser.emp FOR c##schemauser.emp;


GRANT CONNECT TO c##writinguser;

GRANT SELECT, INSERT, UPDATE, DELETE ON c##writinguser.dept TO c##writinguser;
GRANT SELECT, INSERT, UPDATE, DELETE ON c##writinguser.emp TO c##writinguser;


commit;
