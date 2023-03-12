package com.example.AirkiteTracker.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AirkiteTracker.repository.AirkiteRepository;
import com.example.AirkiteTracker.repository.FlightModel;

@Service
public class AirkiteService {
	
	@Autowired
	AirkiteRepository repo;
	@Autowired
	FlightModel[] flights;
	
	private JSONObject js;
	private JSONArray response;
	
	
	private String getTime(String timeFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timeFormat, formatter);
        
        String day = dateTime.getDayOfWeek().toString().substring(0, 3);
        String hours = String.valueOf(dateTime.getHour());
        String minutes = String.valueOf(dateTime.getMinute());
        return day+" "+hours+":"+minutes;
	}
	
	public FlightModel[] returnFlightObjects(String... args) { 
		
		if(args.length == 3)
			js = repo.getFlightSchedules(args[0], args[1]);
		else
			js = repo.getAirportSchedules(args[0], args[1]);
		
		response = js.getJSONArray("response");
		flights = new FlightModel[response.length()];
		
		for (int i=0; i < response.length(); i++) {
		   
			JSONObject flight = response.getJSONObject(i);
			flights[i] = new FlightModel();
			
			flights[i].setAirline(flight.getString("airline_iata"));
			flights[i].setFlightID(flight.getString("flight_iata"));
			flights[i].setDep(flight.getString("dep_iata"));
			flights[i].setDepTime(getTime(flight.getString("dep_time")));
			flights[i].setArr(flight.getString("arr_iata"));
			flights[i].setArrTime(getTime(flight.getString("arr_time")));
			flights[i].setStatus(flight.getString("status"));
		}

		return flights;
	}
	
}
