# Student Management System

A terminal-based Student Management System built with Java and JDBC.

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- MySQL Database
- `mysql-connector-j-version.jar` (placed in `lib/`)

## Database Setup

Run the database_setup.sql file to create the database and tables with this command

```sh
mysql -u root -p < database_setup.sql
```

## Sample data generation

Run the following command from the project root:

```sh
python generate_data.py
```

This will generate a `mock_data.sql` file with sample data for students, attendance, and marks and insert it into the database, run it like

```sh
mysql -u root -p < mock_data.sql
```

## How to Compile

Run the following command from the project root:

```sh
javac -d bin -cp lib/mysql-connector-j-version.jar src/main/java/com/college/sms/**/*.java src/main/java/com/college/sms/Main.java
```

For Windows run this command:

```sh
javac -d bin -cp lib/mysql-connector-j-9.5.0.jar @(Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

## How to Run

after compiling, run:

```sh
java -cp bin:lib/mysql-connector-j-version.jar com.college.sms.Main
```

For Windows run this command:

```sh
java -cp "bin;lib/mysql-connector-j-9.5.0.jar" com.college.sms.Main
```


For confirming if the Admin is registered or not run this command:

```sh
mysql -u root -p
```

then run this command:

```sh
USE student_management;
```

```sh
SELECT * FROM users;
```
