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

import com.ip2n.model.NigeriaState;

@Repository("nigeriaStateDAO")
public class NigeriaStateDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<NigeriaState> getAllNigeriaStates() {

		final String sql = "Select id, name, abbr, created_by, created_on, updated_by, updated_on, govts from nigeria_states";
		List<NigeriaState> states = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<NigeriaState>(NigeriaState.class));
		return states;

	}

	public NigeriaState getNigeriaStateById(long id) {

		final String sql = "Select id, name, abbr, created_by, created_on, updated_by, updated_on, govts from nigeria_states";
		NigeriaState state = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<NigeriaState>(NigeriaState.class));
		return state;

	}

	public long createNigeriaState(final NigeriaState state) {
		final String sql = "insert into nigeria_states (name, abbr, govts, created_by, created_on) VALUES (?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, state.getName());
				pst.setString(2, state.getAbbr());
				pst.setString(3, state.getGovts());
				pst.setString(4, state.getCreatedBy());
				pst.setTimestamp(5,
						new java.sql.Timestamp(new Date().getTime()));

				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public long updateNigeriaState(final NigeriaState state) {
		final String sql = "update nigeria_states set name = ?, abbr = ?, govts = ?, updated_by = ?, updated_on = ? where id = ?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pst = con.prepareStatement(sql,
						new String[] { "id" });

				pst.setString(1, state.getName());
				pst.setString(2, state.getAbbr());
				pst.setString(3, state.getGovts());
				pst.setString(4, state.getUpdatedBy());
				pst.setTimestamp(5,
						new java.sql.Timestamp(new Date().getTime()));
				pst.setLong(6, state.getId());
				return pst;
			}
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return id;
	}

	public int deleteNigeriaState(final long id) {
		final String sql = "delete from nigeria_states where id = " + id;
		int count = jdbcTemplate.update(sql);
		return count;
	}

}
