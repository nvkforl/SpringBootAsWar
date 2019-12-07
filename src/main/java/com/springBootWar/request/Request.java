package com.springBootWar.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

	@JsonProperty("MessageID")
	private String messageID;
	
	@JsonProperty("BarID")
	private String barID;
	
	@JsonProperty("ReqType")
	private String reqType;
	
	@JsonProperty("RentPeriod")
	private String rentPeriod;
	
	
}
