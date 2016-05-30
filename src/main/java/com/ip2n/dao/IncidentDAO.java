package com.ip2n.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ip2n.model.Incident;

@Repository("incidentDAO")
public class IncidentDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Incident> getLatestIncidents(long id) {

		final String sql = "Select id, state, govt, type, created_on, report_date, created_by, status, questions, description, image from incidents where id > "
				+ id + " and status != 'DELETED' order by id desc";
		List<Incident> incidents = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Incident>(Incident.class));
		return incidents;

	}

	public List<Incident> getIncidentsByType(String type) {

		final String sql = "Select id, state, govt, type, created_on, report_date, created_by, status, questions, description, image from incidents where type = '"
				+ type + "' and status != 'DELETED' order by id desc";
		List<Incident> incidents = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Incident>(Incident.class));
		return incidents;

	}

	public List<Incident> getIncidentsByStateType(String type, String state) {

		final String sql = "Select id, state, govt, type, created_on, report_date, created_by, status, questions, description, image from incidents where type = '"
				+ type
				+ "' and state = '"
				+ state
				+ "' and status != 'DELETED' order by id desc";
		List<Incident> incidents = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Incident>(Incident.class));
		return incidents;

	}

	public List<Incident> getIncidentsByPage(int pg, int count) {

		final String sql = "Select id, state, govt, type, created_on, report_date, created_by, status, questions, description, image from incidents where status != 'DELETED' order by id desc LIMIT "
				+ pg + "," + count;
		List<Incident> incidents = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Incident>(Incident.class));
		return incidents;

	}

	public long createIncident(final Incident incident) {
		final String sql = "insert into incidents (questions, state, govt, type, created_on, report_date, created_by, status, description) VALUES (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, incident.getQuestions());

				pst.setString(2, incident.getState());
				pst.setString(3, incident.getGovt());
				pst.setString(4, incident.getType());
				pst.setTimestamp(5,
						new java.sql.Timestamp(new Date().getTime()));
				pst.setTimestamp(6, new java.sql.Timestamp(incident
						.getReportDate().getTime()));
				pst.setString(7, incident.getCreatedBy());
				pst.setString(8, "ACTIVE");
				pst.setString(9, incident.getDescription());
				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public long updateIncident(final Incident incident) {
		final String sql = "update incidents set questions = ?, state = ?, govt = ?, type = ?, report_date = ?, status = ?, description = ? where id = ?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, incident.getQuestions());

				pst.setString(2, incident.getState());
				pst.setString(3, incident.getGovt());
				pst.setString(4, incident.getType());
				pst.setTimestamp(5,
						new java.sql.Timestamp(new Date().getTime()));
				pst.setString(6, "ACTIVE");
				pst.setString(7, incident.getDescription());
				pst.setLong(8, incident.getId());
				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public int deleteIncident(final long id) {
		final String sql = "update incidents set status = 'DELETED' where id = "
				+ id;
		int count = jdbcTemplate.update(sql);
		return count;
	}

	public void saveImage(long id, String name) {
		String sql = "update incidents set image = '" + name + "' where id = "
				+ id;
		jdbcTemplate.update(sql);

	}

	public String getImage(long id) {
		String sql = "Select image from incidents where id = " + id;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

}
