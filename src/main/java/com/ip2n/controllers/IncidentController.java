package com.ip2n.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ip2n.dao.IncidentDAO;
import com.ip2n.model.Incident;
import com.ip2n.vo.CreateResponse;

@RestController
public class IncidentController {

	@Autowired
	IncidentDAO incidentDAO;

	@RequestMapping(value = "/incidents/latest/{id}", produces = "application/json", method = RequestMethod.GET)
	public List<Incident> getLatestIncidents(@PathVariable long id)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Incident> incidents = incidentDAO.getLatestIncidents(id);
		if (incidents == null || incidents.isEmpty()) {
			return null;
		}

		return incidents;
	}

	@RequestMapping(value = "/incidents/page/{pg}/{count}", produces = "application/json", method = RequestMethod.GET)
	public List<Incident> getIncidentsByPage(@PathVariable int pg,
			@PathVariable int count) throws JsonGenerationException,
			JsonMappingException, IOException {
		List<Incident> incidents = incidentDAO.getIncidentsByPage(pg, count);
		if (incidents == null || incidents.isEmpty()) {
			return null;
		}

		return incidents;
	}

	@RequestMapping(value = "/incidents", produces = "application/json", method = RequestMethod.POST)
	public CreateResponse createIncident(@RequestBody Incident incident)
			throws JsonGenerationException, JsonMappingException, IOException {
		long id = incidentDAO.createIncident(incident);
		return new CreateResponse(id);
	}

	@RequestMapping(value = "/incidents", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	public long updateIncident(@RequestBody Incident incident)
			throws JsonGenerationException, JsonMappingException, IOException {
		return incidentDAO.updateIncident(incident);
	}

	@RequestMapping(value = "/incidents/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public int deleteIncidentType(@PathVariable long id) {
		return incidentDAO.deleteIncident(id);
	}

	@RequestMapping(value = "/incidents/image/{id}", method = RequestMethod.POST)
	public long uploadIncidentPicture(
			@RequestParam("file") MultipartFile image, @PathVariable long id)
			throws IOException {

		File file = new File("/pics/incidents/" + id + "/"
				+ image.getOriginalFilename());
		file.getParentFile().mkdirs();

		IOUtils.copy(image.getInputStream(), new FileOutputStream(file));
		incidentDAO.saveImage(id, image.getOriginalFilename());
		System.out.println(image.getOriginalFilename());

		return 0;
	}

	@RequestMapping(value = "/incidents/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] downloadIncidentPicture(@PathVariable long id)
			throws IOException {

		File file = new File("/pics/incidents/" + id + "/"
				+ incidentDAO.getImage(id));
		return IOUtils.toByteArray(new FileInputStream(file));
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
