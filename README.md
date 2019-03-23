# DataCreationTable
Program for creating organised table from user's data. 

# Features
Program uses data from [RandomUser](https://randomuser.me/) and [RandomUserRU](http://randomuser.ru/) APIs or from /resources files for generating users if there is connection problem and unite them to one .XML and .PDF file. Also support MySQL databases, that contains users' data.

# Building

Program include Maven, so it is possible to import project via Maven project by using *pom.xml* file.

The program uses several libraries that are **necessary** for the full functionality of the program.

These libraries are automatically downloaded because of used Maven with written dependencies.

1. For requests was used [Unirest 1.4.9](https://mvnrepository.com/artifact/com.mashape.unirest/unirest-java/1.4.9)

2. For PDF creation was used [iTextPDF Library](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22itextpdf%22)

3. For XML creation was used [POI-4.0.1 Library](http://apache-mirror.rbc.ru/pub/apache/poi/release/bin/poi-bin-4.0.1-20181203.zip)

In **Main.java** class there is one start parameter - max users number, that can be generate.

In **EditorSQL.java** class there are three parameters for connecting to your SQL database server:

1. url, where **fintech** - name of your database.

2. user -  where **root** - your server username,

3. password - where **root** your password.

Recommend to change it if there is a stable internet connection, because program's default value contains max number of users in */resources* files.

Changing output file names:

1.To change the name of the output .PDF file, you must change the parameter *File myFile = new File(your parameter)* in the method *writeFileOut()* of the class *ExcelExport*

2.To change the name of the output .PDF file, you must change the parameter *setPdfFile(new File(your parameter))* in the method *createNewDocumnet()* of the class *PdfExport*

<h2> WARNING </h2>
Cyrillic symbols export to .PDF only work with font from */resources* folder

Program works with **Java Language ver. 1.8** and **JDK-11.0.2**

<h2> Preactions </h2>

1. Create database

2. Create tables: address ( id int auto_increment not null, postcode varchar(256), country varchar(256), region varchar(256), city varchar(256), street varchar(256), house int, flat int, primary key (id) );

persons ( id int auto_increment not null, surname varchar(256), name varchar(256), middlename varchar(256), birthday date, gender varchar(1), inn varchar(12), address_id int not null, foreign key (address_id) references address(id), primary key (id) );

3. In code change url, user and passqord to your parameters (see #Building)

<h2>Actions </h2>

1. Download project

In command line:

2. Set directory to project folder (in Windows cd * *\DataCreationTable*)

3. mvn compile

4. mvn package


# Running

Actions:

In command line:

1.java -Dfile.encoding=utf-8 -jar target/DataCreationTable-1.0-SNAPSHOT.jar


# Results

With enabled internet connection:
Filling database and getting data from it:
Log messages:
1. *File .XLS created. Path: (location of generated .XLS file)*
2. *File .PDF created. Path: (location of generated .PDF file)*

Created .PDF and .XLS file with table with generated persons.

With disabled internet connection:
If database connection is true and database is not empty, program generates files with data from database, else program generates files from local resourses
Log messages:
1. Internet connection trouble, go with default method
2. *File .XLS created. Path: (location of generated .XLS file)*
3. *File .PDF created. Path: (location of generated .PDF file)*

Created .PDF and .XLS file with table with generated persons.
