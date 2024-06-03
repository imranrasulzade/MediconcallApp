# For Up

## 1. Database connection
Datasource url, username, password application.properties faylında declare olunmalı
```properties
spring.datasource.url=your-db-url
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password
```

## 2. Create Tables
1. Liquibase disable olunmalı
```properties
spring.liquibase.enabled=false
```
2. spring.jpa.hibernate.ddl-auto update olaraq dəyişdirilməli
```properties
spring.jpa.hibernate.ddl-auto=update
```
3. Tables yaranması üçün project start.
4. Liquibase enable olunmalı
```properties
spring.liquibase.enabled=true
```
5. Project start.
   
## 3. (Optional changes) 
```properties
#for email config
spring.mail.username=your-email
spring.mail.password=your-email-password
application.my-email=your-email

#files directory - for write and read files
application.files.directory=your-folder-path

```
