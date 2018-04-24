Google-Authenticator-Sample   [![Build Status](https://travis-ci.org/OhadR/Google-Authenticator-Sample.svg?branch=master)](https://travis-ci.org/OhadR/Google-Authenticator-Sample)
=============



This project is a demo project of how to connect Spring-Security with 2-factor-authentication (2FA / MFA)
such as Google Authenticator.



Make it work
------------
* Deploy the WAR on a servlet container, e.g. tomcat.
* this demo app works in front of MySQL server (repository layer)
* Browse http://localhost:8089/secure/hello. The client needs a login, but first it has to register.
* client should access the resource, and print a message.

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