# assignment 1 (assigned on 20160923, done on 20161006)
A practice demo adding basic JDBC + SQL + JSP + Servlet + Javascript support to a static html demo site called 'easybuy'.

## Prerequisites
JDK 1.7+
Gradle 2.1+
MySQL 5.0+ 

## How to build
`gradle war` will create the war file.

## How to run immediately
1. Execute the sql script 'src/main/db/init-db.sql' with your local mysql root user to initial db schema 'easybuy'.
2. Run `gradle jettyRun` to start a jetty server running the webapp.
3. Access 'http://127.0.0.1:4488/assignment-1/' in your browser. The demo user/password is 'admin/admin'.

## How to initalize as eclipse project
Run `gradle clean cleanEclipse eclipse` and then import into your eclipse workspace.

## Licence
MIT
