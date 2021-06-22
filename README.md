# Project Name
> Coding Assignment

## General Information
- Program reads log files with events written in json format and stores them into a file database.
- It also makes some processing before storing data and stores only what is demanded according to assignment instructions.


## Setup
To run the application, you need to have Java 8 and HSQLDB.

Program stores data in a file-type database in subfolder "data" of the root application folder.

Default log level has been set to "info". If you want the logger to print out data from the database, change log level to "debug". 

There is a file "Logfile.txt" in the root directory of the application, which can be used as an input file.


## Usage
In order to run the application, open console and go to the project's root directory called "CodingAssignment".

Then execute the following command:

`gradlew run --args="inputFileName.log"`

where "inputFileName.log" is a path to the input file you want to be processed by the application.

For example:

Windows: `gradlew run --args="D:\DirectoryName\Logfile.log"`

Linux: `gradlew run --args="/home/user/DirectoryName/Logfile.log"`

If you want to test the application, open console and go to the project's root directory called "CodingAssignment". 

Then type below command:

`gradlew clean test`


