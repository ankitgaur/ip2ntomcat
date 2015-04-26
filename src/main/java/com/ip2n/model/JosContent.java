package com.ip2n.model;

import java.util.Date;

public class JosContent {
	private long id;
	private String title;
	private String alias;
	private String introtext;
	private Date created;
	private String user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIntrotext() {
		return introtext;
	}

	public void setIntrotext(String introtext) {
		this.introtext = introtext;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
