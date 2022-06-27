package com.triple.home.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triple.home.model.CommonResponse;
import com.triple.home.service.ReviewService;

@RestController()
public class ReviewEventController {
	
	@Autowired
	private ReviewService reviewService;

	
	/**
	 * 사용자 아이디에 따른 현재 리뷰포인트 조회
	 * @param userId
	 * @return
	 */
	@GetMapping("/point")
	public ResponseEntity<Object> event(@RequestParam("userId") String userId){
		
		CommonResponse commonResponse = reviewService.pointSelete(userId);
		
		return new ResponseEntity<Object>(commonResponse, HttpStatus.OK);
	}
	
}
