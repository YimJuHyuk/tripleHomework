package com.triple.home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.home.model.CommonResponse;
import com.triple.home.model.Event;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private ReviewService reviewService;

	@Override
	public CommonResponse commonEvent(Event event) {
		CommonResponse commonResponse = new CommonResponse();
		switch (event.getType()) {
			case REVIEW:
				commonResponse = reviewService.reviewEvent(event);
				break;
			default:
				commonResponse.setReturnCode("400");
				commonResponse.setMessage("존재하지 않는 이벤트 타입입니다.");
				break;
		}
		
		return commonResponse;
	}
	
}
