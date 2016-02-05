WHAT:
This is a spring boot application that demonstrate:
-simple webapp, rest interface,
-security with database,
-caching,
-search with pagination,
-custom error handling,
-send email,
-init data.

WHAT IS NEEDED:
-java 8,
-postgress database,

HOW TO RUN:
-in database execute /src/sql/tables.sql script.
-from consone: "mvn clean install"
-java -jar target/mh.springboot-1.0.0-SNAPSHOT.jar
-run from Intellij (add "Spring Boot" application),
[to reload page without restarting application on Mac press: cmd-shift f9]

Default users:
-login: "admin",
password: "a" (
RoleEnum.ADMIN, RoleEnum.USER,
-login: "user"
password: "u",
RoleEnum.USER

TO RETHINK:
-in AbstractEntity added fields: OffsetDateTime (still waiting for JPA 2.2, JPA1 doesn't support it, it is better to use JodaTime)

TODO:
-add hashing for password,
-add csrf,
-add unit tests instead integration tests,
-add custom logout, forbidden page,
-add beter spring security tests,
-add more builders.

BRANCHES:
-master - current development
-single_page - serve simple html page. 

