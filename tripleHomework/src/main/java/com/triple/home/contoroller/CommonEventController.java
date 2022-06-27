package com.triple.home.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.triple.home.model.CommonResponse;
import com.triple.home.model.Event;
import com.triple.home.service.CommonService;

import lombok.extern.slf4j.Slf4j;

@RestController("/")
@Slf4j
public class CommonEventController {
	
	@Autowired
	private CommonService commonService;

	/**
	 * 모든 이벤트 요청 받는곳
	 * type : 이벤트 타입 (리뷰이벤트, XXX이벤트 등...)
	 * action : ADD(생성), MOD(수정), DELETE(삭제)
	 * @param review
	 * @return 공통응답 값
	 */
	@PostMapping("event")
	@ResponseBody
	public ResponseEntity<CommonResponse> event(@RequestBody Event event){
		log.info("Event => {}", event);
		
		CommonResponse commonResponse = commonService.commonEvent(event);
		
		return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
	}
	
}
