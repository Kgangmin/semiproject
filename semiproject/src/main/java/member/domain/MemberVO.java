package member.domain;



public class MemberVO {

	// insert + select 용 
	private String user_id;            // 회원아이디
	private String user_pwd;           // 비밀번호 (SHA-256 암호화 대상)
	private String user_name;          // 회원명
	private String email;              // 이메일 (AES-256 암호화/복호화 대상)
	private String mobile;             // 연락처 (AES-256 암호화/복호화 대상) 
	private String birthday;           // 생년월일   
	private int total_payment;		   // 누적결제금액
	private String fk_grade_no;		   // 회원등급번호
	private int point;                 // 포인트
	private String register_date;	   // 가입일자
	private String last_pwd_update;	   // 마지막암호변경날짜시각
	private int is_withdrawn;	   // 회원탈퇴유무
	private int is_active;          // 휴면유무    
	private int access_level;       // 접근권한      
	
	
	private boolean requirePwdChange = false;
	// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
	// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getTotal_payment() {
		return total_payment;
	}
	public void setTotal_payment(int total_payment) {
		this.total_payment = total_payment;
	}
	public String getFk_grade_no() {
		return fk_grade_no;
	}
	public void setFk_grade_no(String fk_grade_no) {
		this.fk_grade_no = fk_grade_no;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getRegister_date() {
		return register_date;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getLast_pwd_update() {
		return last_pwd_update;
	}
	public void setLast_pwd_update(String last_pwd_update) {
		this.last_pwd_update = last_pwd_update;
	}
	public int getIs_withdrawn() {
		return is_withdrawn;
	}
	public void setIs_withdrawn(int is_withdrawn) {
		this.is_withdrawn = is_withdrawn;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public int getAccess_level() {
		return access_level;
	}
	public void setAccess_level(int access_level) {
		this.access_level = access_level;
	}
	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}

	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}

	
	// 얻어온 전화번호 사이에 ( - ) 를 넣는 함수 
	public String getFormattedMobile() {
        if (mobile != null && mobile.length() == 11) {
            return mobile.substring(0, 3) + "-" +
                   mobile.substring(3, 7) + "-" +
                   mobile.substring(7);
        }
        return mobile; // null 또는 잘못된 형식이면 그대로 반환
    }
	
	
	
	
	
}
