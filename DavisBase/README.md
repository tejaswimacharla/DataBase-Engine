# Davisbase CS 6360 Project - 2. 
The goal of this project is to implement a (very) rudimentary database engine that is loosely based on a hybrid between MySQL and SQLite, which I call DavisBase. Your implementation should operate
entirely from the command line and API calls (no GUI).

DavisBase supports only one database and one user which are used by default. User can create multiple tables in the database. Supported commands are as follows.
All the commands are case insensitive and must end with ;. 
DDL
• SHOW TABLES – Displays a list of all tables in DavisBase.
• CREATE TABLE – Creates a new table schema, i.e. a new empty table.
• DROP TABLE – Remove a table schema, and all of its contained data.
DML
• INSERT INTO TABLE – Inserts a single record into a table.
VDL
• “SELECT-FROM-WHERE” -style query.
• EXIT – Cleanly exits the program and saves all table information in non-volatile files.
• HELP - Show this help information.

For demonstration, 
a) Using Eclipse
Extract the folder DavisBase_TM.
Open Eclipse.
Load the folder DavisBase_TM in Eclipse project workspace.
Run the file "DavisBasePrompt.java" as a Java application.

b) From command prompt
Extract the folder DavisBase_TM.
Open Command Prompt.
Navigate to the extracted folder DavisBase_TM path and to DavisBase_TM\src.
Compile all the files using the command ==> javac *.java
Run the DavisBase using the command ==> java DavisBasePrompt


Sample commands to run:

SHOW TABLES;

CREATE TABLE Persons (PersonID INT, LastName TEXT, FirstName TEXT);
CREATE TABLE Employees (ID INT NOT NULL, LastName TEXT, FirstName TEXT NOT NULL);

INSERT INTO Persons VALUES (1, Macharla, Tejaswi); 
INSERT INTO Employees VALUES (1, Macharla, null); // null constraint

SELECT * FROM Persons;
SELECT * FROM Employees; 
SELECT PersonID, LastName, FirstName FROM Persons WHERE PersonID = 1;

DROP TABLE Persons;

EXIT;