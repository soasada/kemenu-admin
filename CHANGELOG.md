# Changelog

## 1.0.47 (12-01-2021)

### Frontend

* Add deletion for admins.

## 1.0.44 (11-01-2021)

### Frontend

* Add `v-model` to `UploadImage`.

## 1.0.43 (11-01-2021)

Public release of the KEMENU admin panel.

### Frontend

* Add quick access for frontend commands in README.

## 1.0.36 (8-1-2021)

### Backend

* Add new service to upload images to Cloudinary.
* Refactor authorization filters: 
  * Remove `JWTAuthorizationManager`.
  * Remove `JWTReactiveAuthorizationManager`.
  * Remove `JWTRoleAuthorizationManager`.
  * Add `JWTReactiveAuthorizationFilter` that acts as authorization filter and creates security context from tokens.
* Now in development logging is printed to console without JSON format, only JSON is used in production with `default` profile.

### Updates

* Update `Spring Boot` from `2.4.0` to `2.4.1`.
* Update `Java` from `11` to `15`.
* Update `Kotlin` from `1.4.10` to `1.4.21`.
* Update `logstash-logback-encoder` from `6.4` to `6.6`.
* Update `java-jwt` from `3.11.0` to `3.12.0`.

## 1.0.35 (18-11-2020)

### Backend

* Remove log from Authorization manager.

## 1.0.34 (18-11-2020)

### Backend

* If Auth header starts with `Basic ` get the authorization bearer token from query string.

## 1.0.33 (18-11-2020)

### Backend

* Add log in Authorization manager.

## 1.0.32 (18-11-2020)

### Backend

* Add multilanguage blog API.

### Frontend

* Refactor of the folder structure.
* Add Vuex for blog and customers components.

## 1.0.30 (16-10-2020)

### CI/CD

* Changed jdk path to jdk folder.

## 1.0.28 (07-10-2020)

### Frontend

* Now SUPER_ADMIN users can create or update new admins.

## 1.0.25 (07-10-2020)

### Frontend

* Search table feature now works when press enter key.
* Improved performance on table component.

### CI/CD

* Add new env variable to use on the pipeline, this 
variable is going to be used to store the release version 
and used in the create release job to publish the 
release on GitHub.
* Add CHANGELOG.md to root folder. In this file we are 
going to store the new changes for each version.
