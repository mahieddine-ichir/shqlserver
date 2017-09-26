# shqlserver
Command line client for MS SQL Server.

*shqlserver* is a command line utility made with Java (Spring JDBC) that forwards any entered sql-request to SQL Server. It is not an SQL Engine, it makes neither transformation nor any syntax check or verification; every submitted sql-request is submitted as is to the SQL Server.

It is a cross platform (Windows, Linux/Unix or Mac OS) provided a Java Runtime (JRE) >= 1.8

# Usage

```
java -jar shsqlserver-<version>.jar --spring.datasource.url=<sqlserver jdbc url> --spring.datasource.username=<sqlserver login/username> - spring.datasource.password=<sqlserver password>
```

## SQL Server Java Driver
*shqlserver* uses *com.microsoft:sqlserver-driver:4.0.2206* driver, providing the *com.microsoft.sqlserver.jdbc.SQLServerDriver* driver class. To use another driver/driver-class, use the parameter *spring.datasource.driver-class-name* (prodiving the driver in the class path of the application) as follows:

```sh
java -jar shsqlserver-<version>.jar ... --spring.datasource.driver-class-name=<your driver class> -cp <path to the j-driver>
```

# Commands and Getting help
All vaid SQL Server syntax is accepted (including stored procedures). Invalid SQL Server syntaxed requests will result in a Java Stack Trace from the SQL Server Java driver.

## Extended commands
Some (sql - mysql like) commands are however supported for ease. To get the list of *available extended* sql commands, type :
```sh
$>sql help
```
