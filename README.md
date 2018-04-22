oAuth2-sample   [![Build Status](https://travis-ci.org/OhadR/oAuth2-sample.svg?branch=master)](https://travis-ci.org/OhadR/oAuth2-sample)   
=============

http://www.asaph.org/2016/04/google-authenticator-2fa-java.html

https://github.com/asaph/twofactorauth


Mainly, this project is a oAuth2 POC, consists of all 3 oAuth parties: the authentication server, a resource server, and a client app.
Each party is represented by its own WAR. In addition, the [Authentication-Flows](#jar-authentication-flows---) 
is a sub-module here.




Make it work
------------
* Deploy all 3 WARs on a servlet container, e.g. tomcat.
* Browse http://localhost:8080/oauth2-client/hello. The client needs a login by itsealf: admin/admin (Spring Security expects your client web-app to have its own credentials).
* client app tries to call the resource-server url http://localhost:8080/oauth2-resource-server/welcome
* This will redirect to oauth2.0 authentication server. Login to authentication-server, currently it is from mem: demo@ohadr.com/demo. it can be configured to read from a DB.
* client should access the resource server using the access-token, and print a message.

