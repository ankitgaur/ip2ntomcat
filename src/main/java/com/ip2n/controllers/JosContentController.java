package com.ip2n.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ip2n.dao.JosContentDAO;
import com.ip2n.model.JosContent;

@RestController
public class JosContentController {

	@Autowired
	JosContentDAO josContentDAO;

	@ResponseBody
	@RequestMapping(value = "/news/{pg}/{count}", produces = "application/json", method = RequestMethod.GET)
	public List<JosContent> getNews(@PathVariable int pg,
			@PathVariable int count) {
		return josContentDAO.getNews(pg, count);
	}

	@ResponseBody
	@RequestMapping(value = "/entertainment/{pg}/{count}", produces = "application/json", method = RequestMethod.GET)
	public List<JosContent> getEntertainment(@PathVariable int pg,
			@PathVariable int count) {
		return josContentDAO.getEntertainment(pg, count);
	}

	@ResponseBody
	@RequestMapping(value = "/news/latest/{id}", produces = "application/json", method = RequestMethod.GET)
	public List<JosContent> getLatestNews(@PathVariable int id) {
		return josContentDAO.getLatestNews(id);
	}

	@ResponseBody
	@RequestMapping(value = "/entertainment/latest/{id}", produces = "application/json", method = RequestMethod.GET)
	public List<JosContent> getLatestEntertainment(@PathVariable int id) {
		return josContentDAO.getLatestEntertainment(id);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	String handleException(Exception ex) {
		if (ex.getMessage().contains("UNIQUE KEY"))
			return "The submitted item already exists.";
		else
			System.out.println(this.getClass() + ": need handleException for: "
					+ ex.getMessage());
		return ex.getMessage();
	}
}
