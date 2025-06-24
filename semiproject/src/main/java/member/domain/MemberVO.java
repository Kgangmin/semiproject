package member.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberVO {

<<<<<<< HEAD
	
	private String userid;             // 회원아이디
	private String pwd;                // 비밀번호 (SHA-256 암호화 대상)
	private String name;               // 회원명
	private String email;              // 이메일 (AES-256 암호화/복호화 대상)
	private String mobile;             // 연락처 (AES-256 암호화/복호화 대상) 
	private String birthday;           // 생년월일   
	private String grade;			   // 회원등급	
	private String point;				   // 포인트
	private String registerday;		   // 가입일자
	private String lastpwdchangedate;  // 마지막암호변경날짜시각
	private String status;				   // 회원탈퇴유무
	private String idle;				   // idle
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}
	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdle() {
		return idle;
	}
	public void setIdle(String idle) {
		this.idle = idle;
	}
	
	

	
	
	
	
=======
	// insert + select 용 
	private String user_id;             // 회원아이디
	private String user_pwd;                // 비밀번호 (SHA-256 암호화 대상)
	private String user_name;               // 회원명
	private String email;              // 이메일 (AES-256 암호화/복호화 대상)
	private String mobile;             // 연락처 (AES-256 암호화/복호화 대상) 
	private String birthday;           // 생년월일   
	private int total_payment;		   // 누적결제금액
	private String fk_grade_no;		   // 회원등급번호
	private int point;                 // 포인트
	private String register_date;	   // 가입일자
	private String last_pwd_update;	   // 마지막암호변경날짜시각
	private String is_withdrawn;	   // 회원탈퇴유무
	private String is_active;          // 휴면유무    
	private String access_level;       // 접근권한      
	
	
	
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

	public String getLast_pwd_update() {
		return last_pwd_update;
	}
	public void setLast_pwd_update(String last_pwd_update) {
		this.last_pwd_update = last_pwd_update;
	}
	public String getIs_withdrawn() {
		return is_withdrawn;
	}
	public void setIs_withdrawn(String is_withdrawn) {
		this.is_withdrawn = is_withdrawn;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getAccess_level() {
		return access_level;
	}
	public void setAccess_level(String access_level) {
		this.access_level = access_level;
	}
	

>>>>>>> branch 'main' of https://github.com/Kgangmin/semiproject.git
	
	
	
	
	
	
	
	
}
