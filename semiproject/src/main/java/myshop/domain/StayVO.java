package myshop.domain;

public class StayVO {

	private String stay_no;					// 숙소번호
	private String stay_name;				// 숙소이름
	private String fk_stay_category_no;		// 카테고리번호
	private String stay_thumbnail;			// 숙박업소사진(썸네일)
	private String stay_info;				// 숙박업소설명(간단한)
	private String stay_tel;				// 숙박업소전화번호
    private double latitude;					// 위도
    private double longitude;					// 경도
    private double stay_score;					// 평점
    private int views;						// 조회수
    private String postcode;				// 우편번호
    private String address;					// 주소
    private String detailaddres;			// 상세주소
    private String extraaddress;			// 참고항목
    
    
	public String getStay_no() {
		return stay_no;
	}
	
	public void setStay_no(String stay_no) {
		this.stay_no = stay_no;
	}
	
	public String getStay_name() {
		return stay_name;
	}
	
	public void setStay_name(String stay_name) {
		this.stay_name = stay_name;
	}
	
	public String getFk_stay_category_no() {
		return fk_stay_category_no;
	}
	
	public void setFk_stay_category_no(String fk_stay_category_no) {
		this.fk_stay_category_no = fk_stay_category_no;
	}
	
	public String getStay_thumbnail() {
		return stay_thumbnail;
	}
	
	public void setStay_thumbnail(String stay_thumbnail) {
		this.stay_thumbnail = stay_thumbnail;
	}
	
	public String getStay_info() {
		return stay_info;
	}
	
	public void setStay_info(String stay_info) {
		this.stay_info = stay_info;
	}
	
	public String getStay_tel() {
		return stay_tel;
	}
	
	public void setStay_tel(String stay_tel) {
		this.stay_tel = stay_tel;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getStay_score() {
		return stay_score;
	}
	
	public void setStay_score(double stay_score) {
		this.stay_score = stay_score;
	}
	
	public int getViews() {
		return views;
	}
	
	public void setViews(int views) {
		this.views = views;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDetailaddres() {
		return detailaddres;
	}
	
	public void setDetailaddres(String detailaddres) {
		this.detailaddres = detailaddres;
	}
	
	public String getExtraaddress() {
		return extraaddress;
	}
	
	public void setExtraaddress(String extraaddress) {
		this.extraaddress = extraaddress;
	}
	
    
    
    
    
}
