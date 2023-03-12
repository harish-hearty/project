package com.example.AirkiteTracker.repository;

import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import okhttp3.OkHttpClient;
import okhttp3.Response;

@Repository
public class AirkiteRepository {
	
	private OkHttpClient client;
	private Response response;
	private String APIkey = "3ded0fe9-727b-4ec1-835c-3343dda21a95";
		
	public JSONObject getFlightSchedules(String dep, String arr) {
		client = new OkHttpClient();
		
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url("https://airlabs.co/api/v9/schedules?dep_iata="+dep+"&arr_iata="+arr+"&api_key="+APIkey)
				.build();
        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
	}
	
	public JSONObject getAirportSchedules(String airport, String way) {
		client = new OkHttpClient();
		
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url("https://airlabs.co/api/v9/schedules?"+way+"_iata="+airport+"&api_key="+APIkey)
				.build();
        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
	}
	
}
