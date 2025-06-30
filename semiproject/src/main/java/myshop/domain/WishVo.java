package myshop.domain;

public class WishVo {
	
	private String w_user_id;
	private String w_stay_no;
	private StayVO stayVO;
	
	public StayVO getStayVO() {
		return stayVO;
	}
	public void setStayVO(StayVO stayVO) {
		this.stayVO = stayVO;
	}
	public String getW_user_id() {
		return w_user_id;
	}
	public void setW_user_id(String w_user_id) {
		this.w_user_id = w_user_id;
	}
	public String getW_stay_no() {
		return w_stay_no;
	}
	public void setW_stay_no(String w_stay_no) {
		this.w_stay_no = w_stay_no;
	}
	
}
