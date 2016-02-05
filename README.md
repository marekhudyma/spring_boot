<br />WHAT:
<br />This is a spring boot application that demonstrate:
<br />-simple webapp, rest interface,
<br />-security with database,
<br />-caching,
<br />-search with pagination,
<br />-custom error handling,
<br />-send email,
<br />-init data.
<br />
<br />WHAT IS NEEDED:
<br />-java 8,
<br />-postgress database,
<br />
<br />HOW TO RUN:
<br />-in database execute /src/sql/tables.sql script.
<br />-from consone: "mvn clean install"
<br />-java -jar target/mh.springboot-1.0.0-SNAPSHOT.jar
<br />-run from Intellij (add "Spring Boot" application),
<br />[to reload page without restarting application on Mac press: cmd-shift f9]
<br />
<br />Default users:
<br />-login: "admin",
<br />password: "a", RoleEnum.ADMIN, RoleEnum.USER,
<br />-login: "user"
<br />password: "u", RoleEnum.USER
<br />
<br />TO RETHINK:
<br />-in AbstractEntity added fields: OffsetDateTime (still waiting for JPA 2.2, JPA1 doesn't support it, it is better to use JodaTime)
<br />
<br />TODO:
<br />-add hashing for password,
<br />-add csrf,
<br />-add unit tests instead integration tests,
<br />-add custom logout, forbidden page,
<br />-add beter spring security tests,
<br />-add more builders.
<br />
<br />BRANCHES:
<br />-master - current development
<br />-single_page - serve simple html page.
