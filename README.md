# tripleHomework
트리플과제

해당 과제는 SpringBoot 환경으로 구축하였습니다.

목차순으로 진행해주세요. (목차는 총 1~4까지 입니다.)

모든 파일 업로드 위치는 README.md 파일과 같은 depth에 올려두었습니다.

1. MySql 스키마 생성 및 DDL, 인덱스 확인 DDL스크립트 파일을 실행해주시면 됩니다.

2. SpringBoot 소스 중 src/main/resource의 yml에서 DB 설정 필요합니다.
datasource에서 사용하고 계신 MySql의 url, username, password을 설정해주시면됩니다.

3. 해당 과제 수행자는 POSTMAN으로 테스트를 진행하였습니다.(포스트맨 컬렉션.json 파일도 올려놨습니다.)
 - 리뷰 작성에 대한 등록/수정/삭제는 제공해주신 POST /events로 진행하였습니다.
   아래는 등록/수정/삭제에 대한 body sample 입니다.
   + 등록
	{
	    "type": "REVIEW",
	    "action": "ADD",
	    "reviewId": "db905d99-74a8-4e68-a626-14f1f31784fb",
	    "content": "좋아요!",
	    "attachedPhotoIds": [
		"{{$guid}}",
		"{{$guid}}"
	    ],
	    "userId": "userTest",
	    "placeId": "place1"
	}
   + 수정
   	{
	    "type": "REVIEW",
	    "action": "MOD",
	    "reviewId": "db905d99-74a8-4e68-a626-14f1f31784fb",
	    "content": "좋아요!!!",
	    "attachedPhotoIds": [
		"a"
	    ],
	    "userId": "userTest",
	    "placeId": "place1"
	}
   + 삭제
   	{
	    "type": "REVIEW",
	    "action": "DELETE",
	    "reviewId": "baec072f-ebe7-4d36-8abf-4aedea157412",
	    "userId": "{{$guid}}",
	    "placeId": "place1"
	}
 - 리뷰점수 조회 API는 GET /point로 진행하였습니다. => http://localhost:8088/point?userId=userTest

4. 테스트케이스도 추가적으로 올려두었습니다.

읽어주셔서 감사합니다.
