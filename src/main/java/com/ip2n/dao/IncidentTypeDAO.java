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

import com.ip2n.model.IncidentType;

@Repository("incidentTypeDAO")
public class IncidentTypeDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<IncidentType> getAllIncidentTypes() {

		final String sql = "Select id, name, created_by, created_on, updated_by, updated_on, questions from incident_types";
		List<IncidentType> types = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IncidentType>(IncidentType.class));
		return types;

	}

	public IncidentType getIncidentTypeById(long id) {

		final String sql = "Select id, name, created_by, created_on, updated_by, updated_on, questions from incident_types";
		IncidentType type = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<IncidentType>(IncidentType.class));
		return type;

	}

	public long createIncidentType(final IncidentType type) {
		final String sql = "insert into incident_types (name, questions, created_by, created_on) VALUES (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, type.getName());
				pst.setString(2, type.getQuestions());
				pst.setString(3, type.getCreatedBy());
				pst.setTimestamp(4,
						new java.sql.Timestamp(new Date().getTime()));

				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public long updateIncidentType(final IncidentType type) {
		final String sql = "update incident_types set name = ?, questions = ?,  updated_by = ?, updated_on = ? where id = ?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, type.getName());
				pst.setString(2, type.getQuestions());
				pst.setString(3, type.getUpdatedBy());
				pst.setTimestamp(4,
						new java.sql.Timestamp(new Date().getTime()));
				pst.setLong(5, type.getId());
				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public int deleteIncidentType(final long id) {
		final String sql = "delete from incident_types where id = " + id;
		int count = jdbcTemplate.update(sql);
		return count;
	}

}
