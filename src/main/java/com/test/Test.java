package com.test;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException, JSONException {

		JSONObject obj = new JSONObject();
		/*
		 * String[] cats = { "House robbery", "Bank robbery", "Kidnap",
		 * "Burglary", "Rape", "Pipeline vandalism", "Oil theft", "Car theft",
		 * "Other" }; obj.append("Crime Type", cats);
		 * 
		 * String[] resp = { "< 30 mins", "1 hr", "2 hrs", "3 hrs", "> 3 hrs" };
		 * obj.append("Police Response Time", resp);
		 * 
		 * obj.append("Nearest Police Station", null);
		 */

		String[] cats = { "Vehicle with no lights", "Dangeous driving",
				"Touts", "Reckless driving", "Robbery", "Traffic jams", "Other" };

		String[] types = { "Car", "Bus", "Taxi", "Danfo", "Okada", "Motorbike",
				"Rail", "Boat", "Other" };

		obj.append("Transport Report Type", cats);
		obj.append("Transport Type", types);

		String[] times = { "< 30 mins", "1 hr", "2 hrs", "3 hrs", "> 3 hrs" };

		/*
		 * String[] causes = { "Dangerious overtaking", "Speeding vehicle",
		 * "Punctured tyre", "Reckless driving", "Bad lighting",
		 * "Bad road/pot holes", "Okada", "Other" };
		 * 
		 * obj.append("Cause of Accident?", causes);
		 * 
		 * String[] resp = { "< 30 mins", "1 hr", "2 hrs", "3 hrs", "> 3 hrs" };
		 * obj.append("Police Response Time", resp);
		 */

		String[] options = { "Yes", "No" };
		obj.append("Traffic Police Present", options);
		obj.append("How long was Traffic Police not present?", times);

		obj.append("Traffic Jam estimates", times);

		System.out.println(obj);

	}
}
