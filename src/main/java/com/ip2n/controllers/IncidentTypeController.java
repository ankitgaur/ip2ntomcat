package com.ip2n.controllers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ip2n.dao.IncidentTypeDAO;
import com.ip2n.model.IncidentType;

@RestController
public class IncidentTypeController {

	@Autowired
	IncidentTypeDAO incidentTypeDAO;

	@RequestMapping(value = "/incidentTypes", produces = "application/json", method = RequestMethod.GET)
	public List<IncidentType> getAllIncidentTypes() throws JsonParseException,
			JsonMappingException, IOException {
		List<IncidentType> types = incidentTypeDAO.getAllIncidentTypes();
		if (types == null || types.isEmpty()) {
			return null;
		}

		return types;
	}

	@RequestMapping(value = "/incidentTypes/{jobId}", produces = "application/json", method = RequestMethod.GET)
	public IncidentType getIncidentTypeById(@PathVariable long id)
			throws JsonParseException, JsonMappingException, IOException {
		IncidentType type = incidentTypeDAO.getIncidentTypeById(id);
		if (type == null) {
			return null;
		}
		return type;
	}

	@RequestMapping(value = "/incidentTypes", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public long createIncidentType(@RequestBody IncidentType typeVO)
			throws JsonGenerationException, JsonMappingException, IOException {
		return incidentTypeDAO.createIncidentType(typeVO);
	}

	@RequestMapping(value = "/incidentTypes", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	public long updateIncidentType(@RequestBody IncidentType typeVO)
			throws JsonGenerationException, JsonMappingException, IOException {
		return incidentTypeDAO.updateIncidentType(typeVO);
	}

	@RequestMapping(value = "/incidentTypes/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public int deleteIncidentType(@PathVariable long id) {
		return incidentTypeDAO.deleteIncidentType(id);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
