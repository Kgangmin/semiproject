package stay.model;

public class StayVO
{
	private String	stay_no;				//	숙박업소번호
	private String	stay_name;			//	숙박업소명
	private String	fk_stay_category_no;	//	숙박업소 카테고리 번호
	private String	stay_thumbnail;		//	숙박업소 썸네일
	private String	stay_info;			//	숙박업소 설명	
	private String	stay_tel;			//	숙박업소 연락처
    private String	stay_address;		//	숙박업소 주소
    private double	latitude;			//	위도
    private double	longitude;           //	경도
    private double	stay_score;          //	숙박업소 평점
    private int		views;					//	숙박업소 조회수
}
