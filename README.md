# Goal of JavaEETestcontainerTry

This project tries to create an integration test example using [Testcontainers](https://github.com/testcontainers/testcontainers-java) against an _Oracle_ database with some special requirements:

# Requirements

**General requirements**:
* Java 8
* Oracle Database (12.1)
* JavaEE 7 application
  * Accessing to Database using _Java EE DataSource_ of application server

**Tools inside application**:
* Hibernate as ORM
* JUnit 5 for tests
* Testcontainers for integration tests

**Database requirements**:
* The user behind the applications data source has only specific DML rights
* There is another user / datasource available with wider DML rights to prepare tests if required (e.g. preparing
test data for special purposes which should not to be in general schema) 
* None of the both users has any DDL rights and they are not the schema owner

**Testcontainer requirements**:
* The application schema with main test data should be inside the container image so test preparation 

**Further requirements**:
* Database uses synonyms to be stage independent

# Others

Feel free to contribute!


# Preparation
## 01 Oracle preparations    
### 01.a Setting up Oracle Database
Inside the Config/sql folder there are scripts to set up the database.
An installed Oracle 12.1 database is needed. Downloadable at [Oracle 12c downloads](https://www.oracle.com/database/technologies/oracle12c-windows-downloads.html).

The script do:
* Create a user which serves as the `schemauser`
* Create tables using Oracles [`DEPT EMP` example script](https://livesql.oracle.com/apex/livesql/file/content_O5AEB2HE08PYEPTGCFLZU9YCV.html)
* Creating synonyms on the tables
* Creating two others users which have
    * select/update privileges (`readinguser`) on the emp table
    * select/insert/update/delete privileges (`writinguser`) on both tables
    
*Note:* My local installation used used the SID `containers`. Therefore the connection-URL for me in this project
(and the example configuration files) is `jdbc:oracle:thin:@localhost:1521:container`    
    
### 01.b Storing JDBC driver in local maven repository
Since late 2019 Oracle has stored the JDBC drivers in a maven repository.
But to access them you [need to register yourself and add additonal maven settings](https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides).

As I didn't want to do this I preferred the old way and adding the JDBC driver locally via `mvn install:install-file`:

`mvn install:install-file -Dfile={Path_to_your_ojdbc.jar} -DgroupId=com.oracle.jdbc -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar`
    
## 02 Setting up Wildfly 18
This step was only done to verify the correct work of the application and hibernate configuration
via manual tests before putting hands on _Testcontainers_.

If you would like to verify it too you have to do the following things

### 02.a Configuring Oracle JDBC driver ### 

Put the JDBC jar (e.g. `ojdb8.jar` ) into the folder `<WILDFLY-DIR>\modules\system\layers\base\com\oracle\main`.
If it doesn't exists (most probably) create it.
Also put the file named `module.xml` (see folder `Config/Wildfly18`) inside it.

After that you have to configure the server so it knows the driver and configure the datasources.
I've put my full _standalone.xml_ of Wildfly (origin folder `<WILDFLY-DIR>\standalone\configuration`)
into the Config/Wildfly18 folder.
Inside the `<datasources>` section there are two datasources (_readingDS_ and _writingDS_ ) defined.
Inside the `<drivers>` section the Oracle JDBC driver is defined.
No other changes of the original file have been made.

### 02.b Creating user for admin console ###

By default no user for the admin console is created so you have to do this.

Execute the `add_user.bat` script inside `<WILDFLY-DIR>\bin`) and add a management user

Source: [mastertheboss.com - wildfly-how-to-add-an-user](http://www.mastertheboss.com/jboss-server/jboss-script/wildfly-how-to-add-an-user )
 

### 02.c Verifyinng the application ###

After setting up the Wildfly you should be able to access the admin console (`http://localhost:9990/`).

There you should see the two datasources which should connect successfully.
 
 
# 03 Manual Check of the application setup 
After setting up the Wildfly, you should also be able to deploy the EAR of the
application after creating it, using a maven build.

You should see a similar output like inside the file `Logs/manualTest.log`:

* Hibernates schema validations was successful
* StartupBean is started
    * First output prints the number of `emps` successfully
    * Second output is an exception because the `readinguser` has no rights to delete


# Tag overview

Some git tags where created to make it easier setting the project to a explicit state:

| Tag          | State description                                                                                                                                           |
|--------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| initialSetup | Setting up database, application and application server. <br> Application can be deployed manually to verify the correct behavior of the datasource. |
|              |                                                                                                                                                 |
|              |                                                                                                                                                 |
