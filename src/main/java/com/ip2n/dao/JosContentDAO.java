package com.ip2n.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ip2n.model.JosContent;

@Repository("josContentDAO")
public class JosContentDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource jomDataSource) {
		this.jdbcTemplate = new JdbcTemplate(jomDataSource);
	}

	public List<JosContent> getLatestEntertainment(long id) {
		final String sql = "SELECT a.id as id,a.title as title,"
				+ " a.alias as alias,a.introtext as introtext,b.name as user,"
				+ " a.created as created FROM jos_content a, jos_users b"
				+ " WHERE a.created_by=b.id and a.sectionid = 10"
				+ " and a.state = 1 and a.id > " + id + " order by id desc";
		List<JosContent> content = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<JosContent>(JosContent.class));
		return content;
	}

	public List<JosContent> getLatestNews(long id) {
		final String sql = "SELECT a.id as id,a.title as title,"
				+ " a.alias as alias,a.introtext as introtext,b.name as user,"
				+ " a.created as created FROM jos_content a, jos_users b"
				+ " WHERE a.created_by=b.id and a.sectionid = 1"
				+ " and a.state = 1 and a.id > " + id + " order by id desc";
		List<JosContent> content = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<JosContent>(JosContent.class));
		return content;
	}

	public List<JosContent> getEntertainment(int pg, int count) {
		final String sql = "SELECT a.id as id,a.title as title,"
				+ " a.alias as alias,a.introtext as introtext,b.name as user,"
				+ " a.created as created FROM jos_content a, jos_users b"
				+ " WHERE a.created_by=b.id and a.sectionid = 10"
				+ " and a.state = 1 order by id desc LIMIT " + pg + "," + count;
		List<JosContent> content = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<JosContent>(JosContent.class));
		return content;
	}

	public List<JosContent> getNews(int pg, int count) {
		final String sql = "SELECT a.id as id,a.title as title,"
				+ " a.alias as alias,a.introtext as introtext,b.name as user,"
				+ " a.created as created FROM jos_content a, jos_users b"
				+ " WHERE a.created_by=b.id and a.sectionid = 1"
				+ " and a.state = 1 order by id desc LIMIT " + pg + "," + count;
		List<JosContent> content = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<JosContent>(JosContent.class));
		return content;
	}
}
