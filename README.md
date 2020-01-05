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
* Database uses synonms to be stage independet

# Others

Feel free to contribute!


# Preparation
## 01 Setting up Oracle Database
Inside the Config/sql foldere there are scripts to set up the database.
An installed Oracle 12.1 database is needed. Downloadable at [Oracle 12c downloads](https://www.oracle.com/database/technologies/oracle12c-windows-downloads.html).

The script do:
* Create a user which serves as the schemauser
* Create tables using Oracles "DEPT EMP" example script
* Creating synonyms on the tables
* Creating two others users which have
    * select/update privileges (readinguser) on the emp table
    * select/insert/update/delete privileges (writinguser) on both tables
    
*Note:* My local installation used used the SID _containers_. Therefore the connection-URL for me in this project
(and the example configuration files) is _jdbc:oracle:thin:@localhost:1521:container_    
    
## 02 Setting up Wildfly 18
This step was only done to verify the correct work of the application and hibernate configuration
via manuel tests before putting hands on _Testcontainers_.

If you would like to verify it too you have to do the following things

### 02.a Configuring Oracle JDBC driver ### 

Put the jdbc jar (e.g. ojdb8.jar ) into the folder _<WILDFLY-DIR>\modules\system\layers\base\com\oracle\main_.
If it doesn't exists (most probably) create it.
Also put the file named _module.xml_ (see Config/Wildfly18) inside it.

After that you have to configure the server so it knows the driver and configure the datasources.
I've put my full _standalone.xml_ of Wildfly (origin folder _<WILDFLY-DIR>\standalone\configuration_) into the Config/Wildfly18 folder.
Inside the _<datasources>_ section there are two datasources (_readingDS_ and _writingDS_ ) defined.
Inside the _<drivers>_ section the Oracle JDBC driver is definied.
No other changes of the original file have been made.

### 02.b Creating user for admin console ###

By default no user for the admin console is created so you have to do this.

Execute the _"add_user.bat"_ script inside _<WILDFLY-DIR>\bin_) and add a management user

Source: [mastertheboss.com - wildfly-how-to-add-an-user](http://www.mastertheboss.com/jboss-server/jboss-script/wildfly-how-to-add-an-user )
 

### 02.c Verifyinng the application ###

After setting up the Wildfly you should be able to access the admin console (_http://localhost:9990/_).

There you should see the two datasources which should connect successfully.
 
You should also be able to deploy the EAR of the application after creating it, using a maven build.


