package com.triple.home.service;

import com.triple.home.model.CommonResponse;
import com.triple.home.model.Event;

public interface ReviewService {

	/**
	 * 리뷰이벤트 생성, 수정, 삭제
	 * @param event
	 * @return
	 */
	CommonResponse reviewEvent(Event event);
	
	CommonResponse pointSelete(String userId);
	
	
	
}
