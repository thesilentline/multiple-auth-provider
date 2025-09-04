# Integrating multiple authentication providers

### Authentication Providers

* Keycloak authentication provider for jwt token
* Oauth authentication provider for opaque token (custom)

### Configuration

* TokenConfig.java
* SecurityConfig.java
* ResourceServerConfig.java

### Description

* TokenConfig.java contains the configuration for the authentication providers
* SecurityConfig.java contains the configuration for the security
* ResourceServerConfig.java contains the configuration for the resource server

Add multiple authentication providers in Spring Boot in resource server config and in run time decide to switch between them using 
custom filter and authentication manager resolver. 