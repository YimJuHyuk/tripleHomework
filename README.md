# tripleHomework
트리플과제

해당 과제는 SpringBoot 환경으로 구축하였습니다.

목차순으로 실행해주세요. (목차는 총 1~4까지 입니다.)

1. 아래는 MySql 스키마 생성 및 DDL, 인덱스 확인 스크립트입니다.
-- 스키마 생성
CREATE SCHEMA `triple` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

-- 리뷰이력 테이블 생성
CREATE TABLE `triple`.`REVIEW_HIST` (
	`ID`	bigint(20)	NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '시퀀스',
	`REVIEW_ID`	VARCHAR(64)	NOT NULL	COMMENT '리뷰번호',
	`USER_ID`	VARCHAR(64)	NOT NULL	COMMENT '작성자 ID',
	`PLACE_ID`	VARCHAR(64)	NOT NULL	COMMENT '작성 장소',
	`ACTION`	VARCHAR(8)	NOT NULL	COMMENT '타입(ADD/MOD/DELETE)',
	`SCORE`	TINYINT	NOT NULL	COMMENT '최대 3점(최초:1/내용:1/사진:1))',
	`STATUS`	VARCHAR(3)	NOT NULL	COMMENT '성공이력(S:성공, F:실패)',
	`CREATE_TIME`	DATETIME	NOT NULL	DEFAULT current_timestamp	COMMENT '등록시간'
);
-- 리뷰이력 테이블 인덱스 생성
-- 리뷰ID로 리뷰이력 확인용
CREATE INDEX REVIEW_HIST_IDX_01 ON `triple`.REVIEW_HIST ( REVIEW_ID );

-- 리뷰 테이블 생성
CREATE TABLE `triple`.`REVIEW` (
	`ID`	bigint(20)	NOT NULL	AUTO_INCREMENT PRIMARY KEY	COMMENT '시퀀스',
	`REVIEW_ID`	VARCHAR(64)	NOT NULL	COMMENT '리뷰번호',
	`CONTENT`	TEXT	NULL	COMMENT '내용',
	`ATTACHED_PHOTO_IDS`	VARCHAR(512)	NULL	COMMENT '첨부된 이미지 ID',
	`USER_ID`	VARCHAR(64)	NOT NULL	COMMENT '작성자 ID',
	`PLACE_ID`	VARCHAR(64)	NOT NULL	COMMENT '작성 장소',
	`SCORE`	TINYINT	NOT NULL	COMMENT '최대 3점(최초:1/내용:1/사진:1))',
	`CREATE_TIME`	DATETIME	NOT NULL	DEFAULT current_timestamp	COMMENT '등록시간',
	`UPDATE_TIME`	DATETIME	NULL	COMMENT '수정시간'
);

-- 리뷰 테이블 인덱스 생성
-- 리뷰ID 중복 방지를 위한 유니크 처리
CREATE UNIQUE INDEX REVIEW_IDX_01 ON `triple`.REVIEW ( REVIEW_ID );
-- 리뷰 총 점수 확인
CREATE INDEX REVIEW_IDX_02 ON `triple`.REVIEW ( USER_ID );
-- 최초 리뷰자 확인
CREATE INDEX REVIEW_IDX_03 ON `triple`.REVIEW ( PLACE_ID );

-- 추가된 인덱스 확인
SHOW INDEX FROM `triple`.REVIEW_HIST;
SHOW INDEX FROM `triple`.REVIEW;

-- 인덱스 타는지 확인 쿼리
-- 해당 장소 리뷰 작성내역 확인
EXPLAIN
SELECT COUNT(PLACE_ID)
FROM `triple`.REVIEW
WHERE PLACE_ID = 'place1';

-- 요청한 사용자의 리뷰점수 총점 확인
EXPLAIN
SELECT USER_ID, SUM(SCORE)
FROM `triple`.REVIEW
WHERE USER_ID = 'userTest'
GROUP BY USER_ID;

-- 리뷰 이력 REVIEW_ID로 조회
EXPLAIN
SELECT COUNT(REVIEW_ID)
FROM `triple`.REVIEW_HIST
WHERE REVIEW_ID = 'place1';

2. SpringBoot yml에서 DB 설정 필요합니다.
datasource에서 사용하고 계신 MySql의 url, username, password을 설정해주시면됩니다.

3. 해당 과제 수행자는 POSTMAN으로 테스트를 진행하였습니다.
포스트맨 컬렉션.json 파일도 올려놨습니다.

4. 테스트케이스도 포스트맨 컬렉션.json과 동일한 depth에 올려두었습니다.

여기까지 읽어주셔서 감사합니다.
