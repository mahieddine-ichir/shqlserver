# shqlserver
Command line client for MS SQL Server.

*shqlserver* is a command line utility made with Java (Spring JDBC) that forwards any entered sql-request to SQL Server. It is not an SQL Engine, it makes neither transformation nor any syntax check or verification; every submitted sql-request is submitted as is to the SQL Server.

It is a cross platform (Windows, Linux/Unix or Mac OS) provided a Java Runtime (JRE) >= 1.8

# Usage

```
java -jar shqlserver-<version>.jar --spring.datasource.url=<sqlserver jdbc url> --spring.datasource.username=<sqlserver login/username> - spring.datasource.password=<sqlserver password>
```

Once run, it will prompt for sql commands as follows

```sh
$> sql
```

## Changing the SQL Server Java Driver
*shqlserver* uses *com.microsoft:sqlserver-driver:4.0.2206* driver (that provides the *com.microsoft.sqlserver.jdbc.SQLServerDriver* driver class). To use another driver/driver-class, use the parameter *spring.datasource.driver-class-name* (prodiving the driver in the class path of the application) as follows:

```sh
java -jar shqlserver-<version>.jar ... --spring.datasource.driver-class-name=<your driver class> -cp <path to the j-driver>
```

# Commands and Getting help
All valid SQL Server requests are accepted (including stored procedures). Invalid SQL Server syntaxed requests will result in a Java Stack Trace from the SQL Server Java driver.

## Extended commands
Some (sql - mysql like) commands are however supported for ease. To get the list of *available extended* sql commands, type

```sh
$>sql help
```

# Examples

* Select the first 10 records from _myschema_, _mytable_, ordered by (auto increment) id (from newest to the oldest)
```sh
$>sql SELECT top 10 * FROM myschema..my_table ORDER BY id DESC
```
or equivalently
```sh
$>sql USE my_schema; SELECT top 10 * FROM my_table ORDER BY id DESC
```

* Execute a stored procedure _myprocedure_ (on _myschema_)
```sh
$>sql USE my_schema; EXEC myprocedure <list of paramters if any>
```

* _Describe_ the structure (column name, type, length, ... etc) of _myschema..mytable_
```sh
$>sql describe my_schema..mytable;
```


# Dockerization
_in progress_
