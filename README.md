# Hair Salon Stylist Manager

#### Manage the client list for your hair stylists

#### By Kevin Mattison

## Create a list of hair stylists, then add and remove clients

## Installation Instructions

Clone this repository:

$ cd ~/Desktop
$ git clone https://github.com/LINK_TO_YOUR_REPO
$ cd hairSalon
Open terminal and run Postgres:

$ postgres
Open a new tab in terminal and create the hair_salon database:

$ psql
$ CREATE DATABASE hair_salon;
$ TABLES stylists, clients
$ STYLISTS COLUMNS id int PRIMARY KEY, lastname varchar, firstname varchar
$ CLIENTS COLUMNS id int PRIMARY KEY, lastname varchar, firstname varchar, stylistid int

Navigate back to the directory where this repository has been cloned and run gradle:

$ gradle run

## Technologies Used

* Java
* SQL
* Gradle
* Spark
* junit
* Velocity
* Fluentlenium
* HTML

### License

Licensed under the GPL.

Copyright (c) 2016 **Kevin Mattison**
