package com.ip2n.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ip2n.dao.StatisticsDAO;
import com.ip2n.model.IncidentTypeStat;
import com.ip2n.model.MultiDimStat;
import com.ip2n.model.RegionStat;
import com.ip2n.vo.Cols;
import com.ip2n.vo.DataTable;
import com.ip2n.vo.IntValue;
import com.ip2n.vo.Rows;
import com.ip2n.vo.StringValue;
import com.ip2n.vo.Value;

@RestController
public class StatisticsController {

	@Autowired
	StatisticsDAO statisticsDAO;

	private Map<String, DataTable> cache = new HashMap<String, DataTable>();

	@RequestMapping(value = "/stats/regions", produces = "application/json", method = RequestMethod.GET)
	public DataTable getAllRegions() {

		DataTable dt = null;

		dt = cache.get("/stats/regions");
		if (dt != null) {
			return dt;
		}

		dt = new DataTable();

		List<Cols> cols = new ArrayList<Cols>();
		dt.setCols(cols);

		List<Rows> rws = new ArrayList<Rows>();
		dt.setRows(rws);

		Cols col1 = new Cols();
		col1.setId("");
		col1.setLabel("State");
		col1.setType("string");

		Cols col2 = new Cols();
		col2.setId("");
		col2.setLabel("Incidents Reported");
		col2.setType("number");

		Cols col3 = new Cols();
		col3.setId("");
		col3.setLabel("name");
		col3.setType("string");

		cols.add(col1);
		cols.add(col2);
		cols.add(col3);

		List<RegionStat> rs = statisticsDAO.getRegionStats();
		for (RegionStat st : rs) {
			List<Value> c = new ArrayList<Value>();

			StringValue v1 = new StringValue();
			v1.setV(st.getAbbr());
			IntValue v2 = new IntValue();
			v2.setV(st.getCount());
			StringValue v3 = new StringValue();
			v3.setV(st.getName());

			c.add(v1);
			c.add(v2);
			c.add(v3);
			Rows rows = new Rows();
			rows.setC(c);
			rws.add(rows);
		}

		cache.put("/stats/regions", dt);
		return dt;
	}

	
	@RequestMapping(value = "/stats/types", produces = "application/json", method = RequestMethod.GET)
	public DataTable getIncidentTypesForAllRegions() {

		DataTable dt = null;

		dt = cache.get("/stats/types");
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO.getIncidentTypeStats();

		dt = fillDataTable(rs, "Incident Type", "Count");
		cache.put("/stats/types", dt);
		return dt;
	}

	@RequestMapping(value = "/stats/types/{region}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getIncidentTypesForRegion(@PathVariable String region) {

		DataTable dt = null;

		dt = cache.get("/stats/types/" + region);
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO.getIncidentTypeStats(region);

		dt = fillDataTable(rs, "Incident Type", "Count");
		cache.put("/stats/types/" + region, dt);
		return dt;
	}

	@RequestMapping(value = "/stats/type/{type}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForType(@PathVariable String type)
			throws JsonGenerationException, JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/type/" + type);
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO.getIncidentTypeStats(null,
				type.replace("_", " "));

		dt = fillDataTable(rs, "Incident Type", "Count");
		cache.put("/stats/type/" + type, dt);
		return dt;
	}

	@RequestMapping(value = "/stats/allstate/{type}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForTypeBystate(@PathVariable String type)
			throws JsonGenerationException, JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/allstate/" + type);
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO
				.getIncidentTypeForAllStates(type.replace("_", " "));

		dt = fillDataTable(rs, "State", "Count");
		cache.put("/stats/allstate/" + type, dt);
		return dt;
	}

	@RequestMapping(value = "/stats/cat/{type}/{cat}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForTypeBystate(@PathVariable String type,
			@PathVariable String cat) throws JsonGenerationException,
			JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/cat/" + type + "/" + cat);
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO.getInciCatStats(
				type.replace("_", " "), cat);

		dt = fillDataTable(rs, "State", "Count");
		cache.put("/stats/cat/" + type + "/" + cat, dt);
		return dt;
	}

	@RequestMapping(value = "/stats/type/{type}/{region}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForTypeRegion(@PathVariable String type,
			@PathVariable String region) throws JsonGenerationException,
			JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/type/" + type + "/" + region);
		if (dt != null) {
			return dt;
		}

		List<IncidentTypeStat> rs = statisticsDAO.getIncidentTypeStats(region,
				type.replace("_", " "));
		dt = fillDataTable(rs, "Incident Type", "Count");
		cache.put("/stats/type/" + type + "/" + region, dt);
		return dt;

	}

	@RequestMapping(value = "/stats/govt/{region}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForGovtState(@PathVariable String region)
			throws JsonGenerationException, JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/govt/" + region);
		if (dt != null) {
			return dt;
		}

		List<MultiDimStat> rs = statisticsDAO.getGovtStatsForState(region);
		dt = fillDataTableM(rs);
		cache.put("/stats/govt/" + region, dt);
		return dt;

	}

	@RequestMapping(value = "/stats/govt", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForGovt() throws JsonGenerationException,
			JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/govt");
		if (dt != null) {
			return dt;
		}

		List<MultiDimStat> rs = statisticsDAO.getGovtStats();
		dt = fillDataTableM(rs);
		cache.put("/stats/govt", dt);
		return dt;

	}

	@RequestMapping(value = "/stats/states", produces = "application/json", method = RequestMethod.GET)
	public DataTable getStatsForStates() throws JsonGenerationException,
			JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/stats/states");
		if (dt != null) {
			return dt;
		}

		List<MultiDimStat> rs = statisticsDAO.getMultiDimRegionStats();
		dt = fillDataTableM(rs);
		cache.put("/stats/states", dt);
		return dt;

	}

	@RequestMapping(value = "/mstats/types", produces = "application/json", method = RequestMethod.GET)
	public DataTable getMDIncidentTypesForAllRegions()
			throws JsonGenerationException, JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/mstats/types");
		if (dt != null) {
			return dt;
		}

		List<MultiDimStat> rs = statisticsDAO.getMultiDimInciStats(null);

		dt = fillDataTableM(rs);
		cache.put("/mstats/types", dt);
		return dt;
	}

	@RequestMapping(value = "/mstats/types/{region}", produces = "application/json", method = RequestMethod.GET)
	public DataTable getMDIncidentTypesForRegion(@PathVariable String region)
			throws JsonGenerationException, JsonMappingException, IOException {

		DataTable dt = null;

		dt = cache.get("/mstats/types/" + region);
		if (dt != null) {
			return dt;
		}

		List<MultiDimStat> rs = statisticsDAO.getMultiDimInciStats(region);

		dt = fillDataTableM(rs);
		cache.put("/mstats/types/" + region, dt);
		return dt;
	}

	private DataTable fillDataTable(List<IncidentTypeStat> rs, String stringx,
			String stringy) {

		DataTable dt = new DataTable();
		List<Cols> cols = new ArrayList<Cols>();
		dt.setCols(cols);

		List<Rows> rws = new ArrayList<Rows>();
		dt.setRows(rws);

		Cols col1 = new Cols();
		col1.setId("");
		col1.setLabel(stringx);
		col1.setType("string");

		Cols col2 = new Cols();
		col2.setId("");
		col2.setLabel(stringy);
		col2.setType("number");

		cols.add(col1);
		cols.add(col2);

		for (IncidentTypeStat st : rs) {
			List<Value> c = new ArrayList<Value>();

			StringValue v1 = new StringValue();
			v1.setV(st.getName());
			IntValue v2 = new IntValue();
			v2.setV(st.getCount());

			c.add(v1);
			c.add(v2);

			Rows rows = new Rows();
			rows.setC(c);
			rws.add(rows);
		}

		return dt;
	}

	private DataTable fillDataTableM(List<MultiDimStat> rs) {

		DataTable dt = new DataTable();
		List<Cols> cols = new ArrayList<Cols>();
		dt.setCols(cols);

		List<Rows> rws = new ArrayList<Rows>();
		dt.setRows(rws);

		/*
		 * List<IncidentTypeStat> rs =
		 * statisticsDAO.getIncidentTypeStats(region, type.replace("_", " "));
		 */

		Set<String> types = new LinkedHashSet<String>();
		Set<String> govts = new LinkedHashSet<String>();

		Map<String, Properties> map = new HashMap<String, Properties>();

		for (MultiDimStat st : rs) {

			if (st.getName() != null && !st.getName().isEmpty()) {
				govts.add(st.getName());
			}
			if (st.getType() != null && !st.getType().isEmpty()) {
				types.add(st.getType());
			}

			if (map.get(st.getName()) == null) {
				if (!st.getName().isEmpty()) {
					Properties prop = new Properties();
					prop.setProperty(st.getType(), "" + st.getCount());
					map.put(st.getName(), prop);
				}
			} else {
				if (!st.getType().isEmpty()) {
					Properties prop = map.get(st.getName());
					prop.setProperty(st.getType(), "" + st.getCount());
				}
			}
		}

		Cols col1 = new Cols();
		col1.setId("");
		col1.setLabel("Local Govt");
		col1.setType("string");

		cols.add(col1);

		for (String type : types) {
			Cols col = new Cols();
			col.setId("");
			col.setLabel(type);
			col.setType("number");
			cols.add(col);
		}

		for (String govt : govts) {

			Properties prop = map.get(govt);

			if (prop != null) {
				List<Value> c = new ArrayList<Value>();
				StringValue v1 = new StringValue();
				v1.setV(govt);
				c.add(v1);
				for (String type : types) {
					String val = prop.getProperty(type);
					if (val != null && !val.isEmpty()) {
						IntValue v2 = new IntValue();
						v2.setV(Integer.parseInt(val));
						c.add(v2);
					} else {
						IntValue v2 = new IntValue();
						v2.setV(0);
						c.add(v2);
					}
				}

				Rows rows = new Rows();
				rows.setC(c);
				rws.add(rows);
			}

		}

		return dt;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	String handleException(Exception ex) {
		if (ex.getMessage().contains("UNIQUE KEY"))
			return "The submitted item already exists.";
		else
			System.out.println(this.getClass() + ": need handleException for: "
					+ ex.getMessage());
		return ex.getMessage();
	}
}
