package com.ohadr.google_authenticator.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final String MFA_CODE_PARAM_NAME = "mfa_code";

    private String verificationCode;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		verificationCode = request.getParameter(MFA_CODE_PARAM_NAME);
	}

	public String getVerificationCode() {
		return verificationCode;
	}
}