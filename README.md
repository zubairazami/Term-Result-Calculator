# Term Result Calculator

A Java based desktop application to serve the purpose of result automation for any Department of Khulna University of Engineering and Technology. It requires MySQL as Database Management System and is responsible for designing its own database. The whole automation process includes student & course enrollment, performance and marks entry, result creation & report generation as pdf.

## External Packages (Included)
* mysql-connector-java-5.1.21-bin.jar
* itextpdf-5.4.3.jar

## Requirements
* Java
* MySQL

## Usage
To run the program (from Command Prompt or Bash) :
```
java -jar TermResultCalculator.jar
``` 

## Special Permission
**Username** & **Password** of a user (with **administrative privilege**) of MySQL will be required only in the first run for creating a new MySQL user named **'trc'** for maintaining and modifying database for the application.

## Issues
* Distorted text & label in User Interface on Ubuntu 13.10+ and Ubuntu based distribution, probably due to hard-coded font selection.

## History
This Project was developed in between **March, 2013** to **October, 2013** as an academic project under the course " **Software Development Project - I** " taken by **Department of Computer Science & Engineering**, **Khulna University of Engineering and Technology**.
