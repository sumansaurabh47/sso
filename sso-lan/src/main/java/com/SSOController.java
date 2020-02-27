package com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSOController {
	
	 @GetMapping("/sso")
	    public String viewHome() {
	        return "hello";
	    }

}
