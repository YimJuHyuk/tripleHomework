package com.triple.home.service;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.triple.home.mapper.ReviewMapper;
import com.triple.home.model.CommonResponse;
import com.triple.home.model.Event;
import com.triple.home.model.ReviewHist;
import com.triple.home.model.ReviewPoint;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;

	/**
	 * 리뷰이벤트 생성, 수정, 삭제
	 */
	@Override
	public CommonResponse reviewEvent(Event review) {
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setReturnCode("200");
		int result = 0;
		boolean isSuccess = true;
		try {
			if(!ObjectUtils.isEmpty(review.getAttachedPhotoIds()) && review.getAttachedPhotoIds().size() > 0) {
				review.setStringAttachedPhotoIds(String.join(",", review.getAttachedPhotoIds()));
			}
			switch (review.getAction()) {
				case ADD:
					// 스코어 점수 셋팅
					scoreResult(review);
					// 리뷰등록
					result = reviewMapper.insert(review);
					commonResponse.setReturnCode("200");
					commonResponse.setMessage(String.format("[%s] %s 이벤트 댓글 %d개 저장 성공", review.getAction(), review.getType(), result));
					break;
					
				case MOD:
					// 스코어 점수 셋팅
					scoreResult(review);
					result = reviewMapper.update(review);
					commonResponse.setReturnCode("200");
					commonResponse.setMessage(String.format("[%s] %s 이벤트 댓글 %d개 업데이트 성공", review.getAction(), review.getType(), result));
					break;
					
				case DELETE:
					result = reviewMapper.delete(review);
					commonResponse.setReturnCode("200");
					commonResponse.setMessage(String.format("[%s] %s 이벤트 댓글 %d개 삭제 성공", review.getAction(), review.getType(), result));
					break;
					
				default:
					commonResponse.setReturnCode("400");
					commonResponse.setMessage(String.format("[%s] action 불일치. 정상적인 값을 입력해주세요.", review.getAction()));
					break;
			}
		} catch (DataIntegrityViolationException e) {
			// 예외 발생시 이력 미생성 또는 isSuccess 분기로 실패에 대한 이력도 관리 가능
			isSuccess = false;
			commonResponse.setReturnCode("400");
			// Duplicate
			if(e.getMessage().contains("Duplicate")) {
				commonResponse.setMessage(String.format("%s이벤트 %s 작업 중 에러가 발생하였습니다. message : reviewId Duplicate Key", review.getType(), review.getAction()));
			}else {
				commonResponse.setMessage(String.format("%s이벤트 %s 작업 중 에러가 발생하였습니다. message : [%s]", review.getType(), review.getAction(), e.getMessage()));
			}
		} catch (Exception e1) {
			// 예외 발생시 이력 미생성 또는 isSuccess 분기로 실패에 대한 이력도 관리 가능
			isSuccess = false;
			commonResponse.setReturnCode("400");
			commonResponse.setMessage(String.format("%s이벤트 %s 작업 중 에러가 발생하였습니다. message : [%s]", review.getType(), review.getAction(), e1.getMessage()));
		}
		
		try {
			// history 저장
			ReviewHist reviewHist = new ReviewHist(review);
			if(isSuccess) {
				reviewHist.setStatus("S");
				log.info("성공 ReviewHist => {}", reviewHist);
				reviewMapper.historyInsert(reviewHist);
			} else {
				reviewHist.setStatus("F");
				log.info("실패 ReviewHist => {}", reviewHist);
				reviewMapper.historyInsert(reviewHist);
			}
		} catch (Exception e) {
			commonResponse.setReturnCode("400");
			commonResponse.setMessage(String.format("%s이벤트 이력저장 작업 중 에러가 발생하였습니다. message : [%s]", review.getType(), e.getMessage()));
		}
		
		return commonResponse;
	}
	
	@Override
	public CommonResponse pointSelete(String userId) {
		ReviewPoint point = new ReviewPoint();
		
		try {
			point = reviewMapper.pointSelect(userId);
			if(!ObjectUtils.isEmpty(point)) {
				point.setReturnCode("200");
				point.setMessage("리뷰포인트 조회 성공");
			}else {
				point = new ReviewPoint();
				point.setReturnCode("200");
				point.setMessage("리뷰포인트 미존재");
				point.setTotalScore(0);
			}
		} catch (Exception e) {
			log.error("", e);
			point.setReturnCode("400");
			point.setMessage(String.format("리뷰포인트 조회 중 에러가 발생하였습니다. message : [%s]", e.getMessage()));
		}
		return point;
	}
	
	/**
	 * 리뷰점수 계산
	 * @param review
	 * @return
	 */
	private int scoreResult(Event review) {
		int score = 0;
		log.info("[{}], SCORE 계산", review.getReviewId());
		
		if(review.getAttachedPhotoIds().size() > 0 && review.getContent().length() > 0) {
			log.info("내용 및 사진 등록. 점수 2점");
			score = 2;
		} else if(review.getAttachedPhotoIds().size() > 0) {
			log.info("사진 등록. 점수 1점");
			score = 1;
		} else if(review.getContent().length() > 0) {
			log.info("내용 등록. 점수 1점");
			score = 1;
		}
		
		// 최초 리뷰 확인하기 위한 조회
		// 해당 장소에 대해서 리뷰작성 내역이 미존재 시 최초리뷰 작성 점수 제공을 위함.
		int reviweCheck = 0;
		try {
			log.info("최초 리뷰 작성자 확인 셀렉트 시작 {}", review.getPlaceId());
			reviweCheck = reviewMapper.select(review.getPlaceId());
			log.info("최초 리뷰 작성자 확인 셀렉트 종료");
		} catch (Exception e) {
			log.error("최초 리뷰 확인 중 에러 : {}", e);
			throw e;
		}
		
		if(reviweCheck == 0) {
			log.info("최초 리뷰 등록. 점수 1점");
			score += 1;
		}
		
		// 리뷰 점수
		review.setScore(score);
		
		log.info("[{}], SCORE 점수 = {}", review.getReviewId(), score);
		
		return score;
	}


	
	
	
}
