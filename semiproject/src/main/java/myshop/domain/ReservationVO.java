package myshop.domain;

public class ReservationVO {

	private String reserv_no;		// 예약 번호
	private String fk_user_id;		// 회원아이디
	private String fk_room_no;		// 객실 번호
    private String checkin_date;	// 체크인날짜
    private String checkout_date;	// 체크아웃 날짜
    private String reserv_date;		// 예약한 날짜(결제한날짜)
    private String imp_uid;			// 결제 식별자
    
    // 부모테이블
    private StayVO stayvo;
    private RoomVO roomvo;
    private boolean review_written;
	public String getReserv_no() {
		return reserv_no;
	}
	
	public void setReserv_no(String reserv_no) {
		this.reserv_no = reserv_no;
	}
	
	public String getFk_user_id() {
		return fk_user_id;
	}
	
	public void setFk_user_id(String fk_user_id) {
		this.fk_user_id = fk_user_id;
	}
	
	public String getFk_room_no() {
		return fk_room_no;
	}
	
	public void setFk_room_no(String fk_room_no) {
		this.fk_room_no = fk_room_no;
	}
	
	public String getCheckin_date() {
		return checkin_date;
	}
	
	public void setCheckin_date(String checkin_date) {
		this.checkin_date = checkin_date;
	}
	
	public String getCheckout_date() {
		return checkout_date;
	}
	
	public void setCheckout_date(String checkout_date) {
		this.checkout_date = checkout_date;
	}
	
	public String getReserv_date() {
		return reserv_date;
	}
	
	public void setReserv_date(String reserv_date) {
		this.reserv_date = reserv_date;
	}

	public StayVO getStayvo() {
		return stayvo;
	}

	public void setStayvo(StayVO stayvo) {
		this.stayvo = stayvo;
	}

	public RoomVO getRoomvo() {
		return roomvo;
	}

	public void setRoomvo(RoomVO roomvo) {
		this.roomvo = roomvo;
	}

	public boolean isReview_written() {
		return review_written;
	}

	public void setReview_written(boolean review_written) {
		this.review_written = review_written;
	}
	private String reserv_status;
	
	public String getReserv_status() {
		return reserv_status;
	}

	public void setReserv_status(String reserv_status) {
		this.reserv_status = reserv_status;
	}

	private boolean canWriteReview;

	public boolean isCanWriteReview() {
	    return canWriteReview;
	}
	public void setCanWriteReview(boolean canWriteReview) {
	    this.canWriteReview = canWriteReview;
	}
	
	public String getImp_uid() {
	    return imp_uid;
	}

	public void setImp_uid(String imp_uid) {
	    this.imp_uid = imp_uid;
	}
}
