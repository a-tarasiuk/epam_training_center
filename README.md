# Gift Certificates System

# Technology stack
`Spring Data JPA` `Spring Security` `JWT`

`Spring Boot` `HATEOAS` `Java 8` `MySQL` `JUnit 5` `Mockito`

## Basic authentification data
User with role `Administrator`:  
- login: `admin`  
- password: `admin`

User with role `User`:  
- login: `user`  
- password: `user`

## Database structure
The SQL code for generating the database structure and for populating the data is on the path `./application/gift_certificates_system/repository/content/`.

![Database structure](./application/gift_certificates_system/repository/content/gift_certificates_system_db.png)

## Task

## Practice

#### Recommended Timeline
The recommended timeline for the whole module is 2 weeks.

### Task

This module is an extension of REST API Advanced module and covers following topics:

1. Spring Security framework
2. Oauth2 and OpenId Connect
3. JWT token

Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications. OAuth 2.0 is a security standard where you give one application permission to access your data in another application. The steps to grant permission, or consent, are often referred to as authorization or even delegated authorization. You authorize one application to access your data, or use features in another application on your behalf, without giving them your password. OpenID Connect (OIDC) is a thin layer that sits on top of OAuth 2.0 that adds login and profile information about the person who is logged in. JSON Web Tokens are JSON objects used to send information between parties in a compact and secure manner.

#### Application requirements

`Main entity` this is a `Gift certificate`.

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.

**User Permissions:**
```
- Guest:
   * Read operations for main entity.
   * Signup.
   * Login.
- User:
   * Make an order on main entity.
   * All read operations.
- Administrator (can be added only via database call):
   * All operations, including addition and modification of entities.
```

4. Get acquainted with the concepts Oauth2 and OpenId Connect
5. (Optional task) Use Oauth2 as an authorization protocol.
    a. OAuth2 scopes should be used to restrict data.
    b. Implicit grant and Resource owner credentials grant should be implemented.
6. (Optional task) It's allowed to use Spring Data. Requirement for this task - all repository (and existing ones) should be migrated to Spring Data.

## Link
[Bcrypt generator Online](https://bcrypt-generator.com)

[JWT Debugger](https://jwt.io/)

# Error
## Intellij Idea Cannot Resolve Symbol
After the project is opened on Intellij IDEA, a situation may arise in which the code is compiled without errors, but when any class is opened, we will observe the picture that some classes will be released in red with an error
`Intellij Idea Cannot Resolve Symbol`.  

It is recommended to perform `File | Invalidate Caches`:

![File | Invalidate Caches](./content/intellij_idea_file_invalidate_caches.png)

If it didn't help, then:  

![Maven](./content/maven_reload_all_maven_projects.png)
