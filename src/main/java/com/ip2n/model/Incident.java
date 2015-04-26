package com.ip2n.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.ip2n.vo.IncidentVO;

public class Incident {

	private long id;
	private String type;
	private String image;
	private String state;
	private String govt;
	private String description;
	private String questions;
	private String status;
	private Date reportDate;
	private String createdBy;
	private Date createdOn;

	public Incident() {

	}

	public Incident(IncidentVO incidentVO) throws JsonGenerationException,
			JsonMappingException, IOException {
		id = incidentVO.getId();
		type = incidentVO.getType();

		state = incidentVO.getState();
		govt = incidentVO.getGovt();
		description = incidentVO.getDescription();
		status = incidentVO.getStatus();
		reportDate = incidentVO.getReportDate();
		createdBy = incidentVO.getCreatedBy();
		createdOn = incidentVO.getCreatedOn();

		if (incidentVO.getQuestions() != null
				&& !incidentVO.getQuestions().isEmpty()) {

			ObjectWriter writer = new ObjectMapper().writer();
			questions = writer.writeValueAsString(incidentVO.getQuestions());
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

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
