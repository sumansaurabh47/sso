package com;

import javax.servlet.http.HttpSession;

import org.opensaml.saml2.core.impl.NameIDImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(HttpSession session) {
		NameIDImpl principal = (NameIDImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = principal.getValue();
		session.setAttribute("userName", email);
		return "index";
	}
}