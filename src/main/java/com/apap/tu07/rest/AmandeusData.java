package com.apap.tu07.rest;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*amandeusDetail*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmandeusData {
	private String airport;
	
	public void setAiport(String name) {
		this.airport = name;
	}
	
	public String getAiport() {
		return airport;
	}

}
