package com.triple.home.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ReviewPoint extends CommonResponse {

	@JsonIgnore
	private String userId;
	private int totalScore;
}
