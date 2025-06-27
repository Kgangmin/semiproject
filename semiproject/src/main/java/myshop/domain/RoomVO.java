package myshop.domain;

public class RoomVO {
	
	    private String room_no;        	// 객실 번호
	    private String fk_stay_no;      // 숙소 번호
	    private String room_grade;      // 객실 등급
	    private String room_thumbnail;  // 객실 이미지(섬네일)
	    private int price_per_night;    // 1박 가격
	    private String room_info;       // 객실 설명
	    
		public String getRoom_no() {
			return room_no;
		}
		
		public void setRoom_no(String room_no) {
			this.room_no = room_no;
		}
		
		public String getFk_stay_no() {
			return fk_stay_no;
		}
		
		public void setFk_stay_no(String fk_stay_no) {
			this.fk_stay_no = fk_stay_no;
		}
		
		public String getRoom_grade() {
			return room_grade;
		}
		
		public void setRoom_grade(String room_grade) {
			this.room_grade = room_grade;
		}
		
		public String getRoom_thumbnail() {
			return room_thumbnail;
		}
		
		public void setRoom_thumbnail(String room_thumbnail) {
			this.room_thumbnail = room_thumbnail;
		}
		
		public int getPrice_per_night() {
			return price_per_night;
		}
		
		public void setPrice_per_night(int price_per_night) {
			this.price_per_night = price_per_night;
		}
		
		public String getRoom_info() {
			return room_info;
		}
		
		public void setRoom_info(String room_info) {
			this.room_info = room_info;
		}
}
