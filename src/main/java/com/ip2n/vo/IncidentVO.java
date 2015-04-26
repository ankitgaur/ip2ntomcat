package com.ip2n.vo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.ip2n.model.Incident;

public class IncidentVO {

	private long id;
	private String type;

	private String state;
	private String govt;
	private String description;
	private Map<String, String> questions;
	private String status;
	private Date reportDate;
	private String createdBy;
	private Date createdOn;

	public IncidentVO() {

	}

	public IncidentVO(Incident incident) throws JsonGenerationException,
			JsonMappingException, IOException {
		id = incident.getId();
		type = incident.getType();

		state = incident.getState();
		govt = incident.getGovt();
		description = incident.getDescription();
		status = incident.getStatus();
		reportDate = incident.getReportDate();
		createdBy = incident.getCreatedBy();
		createdOn = incident.getCreatedOn();

		if (incident.getQuestions() != null
				&& !incident.getQuestions().isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(incident.getQuestions(),
					new TypeReference<HashMap<String, String>>() {
					});
			questions = map;
		}

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGovt() {
		return govt;
	}

	public void setGovt(String govt) {
		this.govt = govt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<String, String> questions) {
		this.questions = questions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
