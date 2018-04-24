package com.ohadr.google_authenticator.authentication;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private static Logger log = Logger.getLogger(CustomWebAuthenticationDetails.class);
    private static final String MFA_CODE_PARAM_NAME = "mfa_code";

    private String verificationCode;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		verificationCode = request.getParameter(MFA_CODE_PARAM_NAME);
		log.debug("verification code= " + verificationCode);
	}

	public String getVerificationCode() {
		return verificationCode;
	}
}