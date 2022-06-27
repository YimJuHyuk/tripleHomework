package com.triple.home.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.triple.home.model.Event;
import com.triple.home.model.ReviewPoint;
import com.triple.home.model.ReviewHist;

@Mapper
public interface ReviewMapper {
	
	int select(@Param("placeId") String placeId);

	int insert(Event event);
	
	int update(Event event);
	
	int delete(Event event);
	
	int historyInsert(ReviewHist reviewHist);
	
	ReviewPoint pointSelect(@Param("userId") String userId);
	
}
