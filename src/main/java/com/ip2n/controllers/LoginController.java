package com.ip2n.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ip2n.vo.SuccessResponse;

@RestController
public class LoginController {

	@RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
	public SuccessResponse getLatestIncidents() {
		return new SuccessResponse("SUCCESS");
	}

}
