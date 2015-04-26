package com.ip2n.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.ip2n.vo.NigeriaStateVO;

public class NigeriaState {

	private long id;
	private String name;
	private String abbr;
	private String govts;
	private String createdBy;
	private String updatedBy;
	private Date createdOn;
	private Date updatedOn;

	public NigeriaState() {

	}

	public NigeriaState(NigeriaStateVO stateVO) throws JsonGenerationException,
			JsonMappingException, IOException {

		id = stateVO.getId();
		name = stateVO.getName();
		abbr = stateVO.getAbbr();

		if (stateVO.getGovts() != null && !stateVO.getGovts().isEmpty()) {

			ObjectWriter writer = new ObjectMapper().writer();
			govts = writer.writeValueAsString(stateVO.getGovts());
		}

		createdBy = stateVO.getCreatedBy();
		createdOn = stateVO.getCreatedOn();
		updatedBy = stateVO.getUpdatedBy();
		updatedOn = stateVO.getUpdatedOn();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getGovts() {
		return govts;
	}

	public void setGovts(String govts) {
		this.govts = govts;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
