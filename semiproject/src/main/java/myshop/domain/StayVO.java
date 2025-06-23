package myshop.domain;

public class StayVO {

	private String stay_no;					// 숙소번호
	private String stay_name;				// 숙소이름
	private String fk_stay_category_no;		// 카테고리번호
	private String stay_thumbnail;			// 숙박업소사진(썸네일)
	private String stay_info;				// 숙박업소설명(간단한)
	private String stay_tel;				// 숙박업소전화번호
    private int latitude;					// 위도
    private int longitude;					// 경도
    private int stay_score;					// 평점
    private int views;						// 조회수
    private String postcode;				// 우편번호
    private String address;					// 주소
    private String detailaddres;			// 상세주소
    private String extraaddress;			// 참고항목
}
