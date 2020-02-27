package com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.SystemUtils;

@RestController
public class SSOController {

	@GetMapping("/sso")
	public String viewHome() {
		return "hello";
	}

	@GetMapping("/apache")
	public String getApache() {
		return SystemUtils.USER_NAME;
	}

}
