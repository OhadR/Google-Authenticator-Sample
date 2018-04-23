Google-Authenticator-Sample   [![Build Status](https://travis-ci.org/OhadR/Google-Authenticator-Sample.svg?branch=master)](https://travis-ci.org/OhadR/Google-Authenticator-Sample)
=============



This project is a xxx POC,  




Make it work
------------
* Deploy the WAR on a servlet container, e.g. tomcat.
* Browse http://localhost:8080/oauth2-client/hello. The client needs a login by itsealf: admin/admin (Spring Security expects your client web-app to have its own credentials).
* client should access the resource using the access-token, and print a message.

References
----
http://www.asaph.org/2016/04/google-authenticator-2fa-java.html

https://github.com/asaph/twofactorauth
