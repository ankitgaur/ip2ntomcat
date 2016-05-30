package com.ip2n.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ip2n.model.Incident;
import com.ip2n.model.IncidentType;
import com.ip2n.model.IncidentTypeStat;
import com.ip2n.model.MultiDimStat;
import com.ip2n.model.NigeriaState;
import com.ip2n.model.RegionStat;
import com.ip2n.vo.IncidentVO;

@Repository("statisticsDAO")
public class StatisticsDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	NigeriaStateDAO nigeriaStateDAO;

	@Autowired
	IncidentDAO incidentDAO;

	@Autowired
	IncidentTypeDAO incidentTypeDAO;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<MultiDimStat> getGovtStats() {
		final String sql = "SELECT govt as name,type as type,count(*) as count FROM `incidents` group by govt,type";
		List<MultiDimStat> mdStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MultiDimStat>(MultiDimStat.class));
		return mdStats;
	}

	public List<MultiDimStat> getGovtStatsForState(String abbr) {

		NigeriaState state = nigeriaStateDAO.getNigeriaStateByAbbr(abbr);
		final String sql = "SELECT govt as name,type as type,count(*) as count FROM `incidents` group by govt,type,state having state = (Select name from nigeria_states where abbr='"
				+ abbr + "')";
		List<MultiDimStat> mdStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MultiDimStat>(MultiDimStat.class));
		// Get govt list from itypestats
		Set<String> govset = new HashSet<String>();
		for (MultiDimStat i : mdStats) {
			govset.add(i.getName());
		}

		// get govts for that state
		String temp = state.getGovts();
		if (temp != null && !temp.isEmpty()) {
			String[] govts = temp.replace("[", "").replace("]", "")
					.replace("\"", "").split(",");
			for (String govt : govts) {
				if (!govset.contains(govt)) {
					// if a govt is missing add it with a count 0
					MultiDimStat istat = new MultiDimStat();
					istat.setCount(0);
					istat.setName(govt);
					istat.setType("");
					mdStats.add(istat);
					govset.add(govt);
				}
			}
		}

		return mdStats;
	}

	public List<MultiDimStat> getMultiDimRegionStats() {

		final String sql = "Select states.name as name, COALESCE(inci.type, '') as type, COALESCE(inci.count, 0) as count from nigeria_states states left join (SELECT state,type,count(*) as count FROM incidents group by state,type) inci on states.name = inci.state";
		List<MultiDimStat> regionStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MultiDimStat>(MultiDimStat.class));
		return regionStats;
	}

	public List<RegionStat> getRegionStats() {

		final String sql = "Select states.name as name, states.abbr as abbr, COALESCE(inci.count, 0) as count from nigeria_states states left join (SELECT state,count(*) as count FROM incidents group by state) inci on states.name = inci.state";
		List<RegionStat> regionStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<RegionStat>(RegionStat.class));
		return regionStats;
	}

	public List<IncidentTypeStat> getIncidentTypeStats() {

		final String sql = "Select types.name as name, COALESCE(inci.count, 0) as count from incident_types types left join (SELECT type,count(*) as count FROM incidents group by type) inci on types.name = inci.type";
		List<IncidentTypeStat> itypeStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IncidentTypeStat>(
						IncidentTypeStat.class));
		return itypeStats;
	}

	public List<MultiDimStat> getMultiDimInciStats(String region)
			throws JsonGenerationException, JsonMappingException, IOException {

		List<MultiDimStat> itypeStats = new ArrayList<MultiDimStat>();
		List<IncidentType> itypes = incidentTypeDAO.getAllIncidentTypes();

		for (IncidentType itype : itypes) {

			List<IncidentTypeStat> istatlist = getIncidentTypeStats(null,
					itype.getName());

			for (IncidentTypeStat istat : istatlist) {
				MultiDimStat mDimStat = new MultiDimStat();
				mDimStat.setName(itype.getName());
				mDimStat.setType(istat.getName());
				mDimStat.setCount(istat.getCount());
				itypeStats.add(mDimStat);
			}

		}

		return itypeStats;
	}

	public List<IncidentTypeStat> getInciCatStats(String type, String cat)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Incident> incidents = incidentDAO.getIncidentsByType(type);

		List<IncidentTypeStat> stats = new ArrayList<IncidentTypeStat>();
		Map<String, Integer> incistat = new HashMap<String, Integer>();

		String key = getKeyByType(type);
		for (Incident incident : incidents) {
			IncidentVO ivo = new IncidentVO(incident);
			String tcat = ivo.getQuestions().get(key);
			if (tcat.equals(cat)) {
				if (incistat.containsKey(incident.getState())) {
					int cnt = incistat.get(incident.getState());
					incistat.put(incident.getState(), cnt + 1);
				} else {
					incistat.put(incident.getState(), 1);
				}
			}

		}

		Set<String> states = incistat.keySet();

		for (String state : states) {
			IncidentTypeStat ins = new IncidentTypeStat();
			ins.setName(state);
			ins.setCount(incistat.get(state));
			stats.add(ins);
		}

		return stats;
	}

	public List<IncidentTypeStat> getIncidentTypeStats(String region) {

		final String sql = "Select types.name as name, COALESCE(inci.count, 0) as count from incident_types types left join (SELECT type,count(*) as count FROM incidents group by type,state having state = (Select name from nigeria_states where abbr ='"
				+ region + "')) inci on types.name = inci.type";
		List<IncidentTypeStat> itypeStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IncidentTypeStat>(
						IncidentTypeStat.class));
		return itypeStats;
	}

	public List<IncidentTypeStat> getIncidentTypeForAllStates(String type) {

		final String sql = "SELECT states.name AS name, COALESCE(COUNT(*) - 1,0) AS count"
				+ " FROM nigeria_states states"
				+ " LEFT JOIN (Select * from incidents where type =  '"
				+ type
				+ "') inci"
				+ " ON states.name = inci.state"
				+ " GROUP BY states.name";

		List<IncidentTypeStat> itypeStats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IncidentTypeStat>(
						IncidentTypeStat.class));
		return itypeStats;
	}

	public List<IncidentTypeStat> getIncidentTypeStats(String region,
			String type) throws JsonGenerationException, JsonMappingException,
			IOException {

		List<IncidentTypeStat> itypeStats = new ArrayList<IncidentTypeStat>();
		Map<String, Integer> tmp = new HashMap<String, Integer>();
		List<Incident> incidents = null;

		String state = null;
		if (region == null && type == null) {
			return itypeStats;
		}

		if (type == null) {
			return itypeStats;
		}

		String key = getKeyByType(type);
		if (key == null) {
			return itypeStats;
		}

		if (region != null) {
			state = nigeriaStateDAO.getNigeriaStateByAbbr(region).getName();
		}

		if (state != null && !state.isEmpty()) {
			incidents = incidentDAO.getIncidentsByStateType(type, state);

		} else {
			incidents = incidentDAO.getIncidentsByType(type);
		}

		if (incidents != null && !incidents.isEmpty()) {
			for (Incident incident : incidents) {
				IncidentVO ivo = new IncidentVO(incident);
				String cat = ivo.getQuestions().get(key);
				if (tmp.get(cat) == null) {
					tmp.put(cat, 1);
				} else {
					int cnt = tmp.get(cat);
					tmp.put(cat, cnt + 1);
				}
			}
		}

		if (!tmp.isEmpty()) {
			Set<String> keys = tmp.keySet();
			for (String ky : keys) {
				if (ky != null && !ky.equals("null") && !ky.isEmpty()) {
					int tcnt = tmp.get(ky);
					IncidentTypeStat istat = new IncidentTypeStat();
					istat.setName(ky);
					istat.setCount(tcnt);
					itypeStats.add(istat);
				}
			}
		}

		return itypeStats;
	}

	private String getKeyByType(String type) {

		if (type.equals("CRIME")) {
			return "Crime Type";
		}

		if (type.equals("FAKE PRODUCTS")) {
			return "Fake Product Type";
		}

		if (type.equals("ROADS")) {
			return "Road Incident Type";
		}

		if (type.equals("HOSPITALS")) {
			return "Hospital Report Type";
		}

		if (type.equals("ACCIDENTS")) {
			return "Accident Type";
		}

		if (type.equals("SCHOOL")) {
			return "School Report Type";
		}

		if (type.equals("POWER")) {
			return "Power Report Type";
		}

		if (type.equals("POTABLE WATER")) {
			return "Water Outage Type";
		}

		if (type.equals("PETROL PRICE")) {
			return "Report Type";
		}

		if (type.equals("AIRPORTS")) {
			return "Airport Report Type";
		}

		if (type.equals("TRANSPORT")) {
			return "Transport Report Type";
		}

		return null;
	}

}
