package com.ohadr.google_authenticator.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Ryan Heaton
 * @author Dave Syer
 */
@Controller
@RequestMapping("/secure/hello")
public class DemoController
{
    @RequestMapping(method = RequestMethod.GET)
	public String demo(ModelMap model) throws Exception {
		return "hello";
	}
}
