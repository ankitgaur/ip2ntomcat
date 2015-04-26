package com.ip2n.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ip2n.dao.NigeriaStateDAO;
import com.ip2n.model.NigeriaState;
import com.ip2n.vo.NigeriaStateVO;

@RestController
public class NigeriaStateController {

	@Autowired
	NigeriaStateDAO nigeriaStateDAO;

	@RequestMapping(value = "/nigeriaStates", produces = "application/json", method = RequestMethod.GET)
	public List<NigeriaStateVO> getAllNigeriaStates()
			throws JsonParseException, JsonMappingException, IOException {
		List<NigeriaState> states = nigeriaStateDAO.getAllNigeriaStates();
		if (states == null || states.isEmpty()) {
			return null;
		}

		List<NigeriaStateVO> statesVO = new ArrayList<NigeriaStateVO>();

		for (NigeriaState state : states) {
			statesVO.add(new NigeriaStateVO(state));
		}

		return statesVO;
	}

	@RequestMapping(value = "/nigeriaStates/{jobId}", produces = "application/json", method = RequestMethod.GET)
	public NigeriaStateVO getNigeriaStateById(@PathVariable long id)
			throws JsonParseException, JsonMappingException, IOException {
		NigeriaState state = nigeriaStateDAO.getNigeriaStateById(id);
		if (state == null) {
			return null;
		}
		return new NigeriaStateVO(state);
	}

	@RequestMapping(value = "/nigeriaStates", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public long createNigeriaState(@RequestBody NigeriaStateVO stateVO)
			throws JsonGenerationException, JsonMappingException, IOException {
		return nigeriaStateDAO.createNigeriaState(new NigeriaState(stateVO));
	}

	@RequestMapping(value = "/nigeriaStates", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	public long updateNigeriaState(@RequestBody NigeriaStateVO stateVO)
			throws JsonGenerationException, JsonMappingException, IOException {
		return nigeriaStateDAO.updateNigeriaState(new NigeriaState(stateVO));
	}

	@RequestMapping(value = "/nigeriaStates/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public int deleteNigeriaState(@PathVariable long id) {
		return nigeriaStateDAO.deleteNigeriaState(id);
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
