package myshop.domain;

public class StayLocationVO {
    private String stay_no;
    private String stay_name;
    private double latitude;
    private double longitude;
    private int    minPrice;
    private String stay_thumbnail;
    
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
	
	public int getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	
	public String getStay_thumbnail() {
		return stay_thumbnail;
	}
	
	public void setStay_thumbnail(String stay_thumbnail) {
		this.stay_thumbnail = stay_thumbnail;
	}
    
    
    
}
