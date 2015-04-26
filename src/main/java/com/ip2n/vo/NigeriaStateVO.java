package com.ip2n.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.ip2n.model.NigeriaState;

public class NigeriaStateVO {

	private long id;
	private String name;
	private String abbr;
	private List<String> govts;
	private String createdBy;
	private String updatedBy;
	private Date createdOn;
	private Date updatedOn;

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

	public List<String> getGovts() {
		return govts;
	}

	public void setGovts(List<String> govts) {
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

	public NigeriaStateVO() {
	}

	public NigeriaStateVO(NigeriaState state) throws JsonParseException,
			JsonMappingException, IOException {
		id = state.getId();
		name = state.getName();
		abbr = state.getAbbr();

		if (state.getGovts() != null && !state.getGovts().isEmpty()) {
			List<String> cats = new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
			cats = mapper.readValue(state.getGovts(),
					new TypeReference<ArrayList<String>>() {
					});
			govts = cats;
		}

		createdBy = state.getCreatedBy();
		createdOn = state.getCreatedOn();
		updatedBy = state.getUpdatedBy();
		updatedOn = state.getUpdatedOn();
	}
}
