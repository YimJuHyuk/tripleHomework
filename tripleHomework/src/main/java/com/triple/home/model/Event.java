package com.triple.home.model;

import java.util.Date;
import java.util.List;

import com.triple.home.common.EventAction;
import com.triple.home.common.EventType;

import lombok.Data;

@Data
public class Event {
	
	private EventType type; 					// 이벤트타입(리뷰이벤트, xx이벤트 등..)
	private EventAction action;					// ADD(생성), MOD(수정), DELETE(삭제)
	private String reviewId;					// 어떤 리뷰에 대한 이벤트인지 가리키는 번호
	private String content;						// 리뷰내용
	private List<String> attachedPhotoIds;		// 리뷰에 첨부된 이미지들의 ID 값
	private String stringAttachedPhotoIds;		// 리뷰에 첨부된 이미지들의 ID 값을 String으로 변환
	private String userId;						// 작성자 ID
	private String placeId;						// 작성된 장소의 ID
	private int score;							// 리뷰점수
	private Date createTime;					// 생성시간
	private Date updateTime;					// 수정시간
}
