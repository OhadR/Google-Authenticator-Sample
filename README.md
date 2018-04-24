Google-Authenticator-Sample   [![Build Status](https://travis-ci.org/OhadR/Google-Authenticator-Sample.svg?branch=master)](https://travis-ci.org/OhadR/Google-Authenticator-Sample)
=============



This project is a xxx POC,  




Make it work
------------
* Deploy the WAR on a servlet container, e.g. tomcat.
* Browse http://localhost:8080/oauth2-client/hello. The client needs a login by itsealf: admin/admin (Spring Security expects your client web-app to have its own credentials).
* client should access the resource using the access-token, and print a message.

How to run?
-----------
The server is a web application, so it should be deployed on a servlet container such as Tomcat.

The easiest way is to use tomcat-maven-plugin, by 

>mvn tomcat7:run

References
----
http://www.asaph.org/2016/04/google-authenticator-2fa-java.html

https://github.com/asaph/twofactorauth

http://www.baeldung.com/spring-security-two-factor-authentication-with-soft-token