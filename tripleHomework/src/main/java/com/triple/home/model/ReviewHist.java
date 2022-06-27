package com.triple.home.model;

import com.triple.home.common.EventAction;
import com.triple.home.common.EventType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewHist {
	private EventType type; 
	private String reviewId;
	private String userId;
	private String placeId;
	private EventAction action;
	private int score;
	private String status;
	
	public ReviewHist(Event review) {
		this.type = review.getType();
		this.reviewId = review.getReviewId();
		this.userId = review.getUserId();
		this.placeId = review.getPlaceId();
		this.action = review.getAction();
		this.score = review.getScore();
	}
	
	
}
