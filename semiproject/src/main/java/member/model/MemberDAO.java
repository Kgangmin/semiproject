package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import myshop.domain.ReservationVO;

public interface MemberDAO {

   //   ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다) 
   boolean idDuplicateCheck(String userid) throws SQLException;

   //   이메일 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
   boolean emailDuplicateCheck(String email) throws SQLException;

   //   로그인   처리
   MemberVO login(Map<String, String> paraMap) throws SQLException;

   //   회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
   int registerMember(MemberVO member) throws SQLException;

   //   패스워드가 맞는지 확인 하는 메소드 
   boolean pwdcheck(Map<String, String> paramap) throws SQLException;

   //   유저의 이메일을 변경하는 메소드 
   int changeEmailMember(Map<String, String> paramap) throws SQLException;
   
   //   아이디찾기
   String findUserid(Map<String, String> paraMap) throws SQLException;

   //   비밀번호 찾기
   boolean isUserExist(Map<String, String> paraMap) throws SQLException;

   //   비밀번호 변경하기
   int pwdUpdate(Map<String, String> paraMap) throws SQLException;

   //	암호화된 입력받은 비밀번호를 유저테이블의 비밀번호와 비교
   boolean checkPassword(String user_id, String currentPwd) throws SQLException;

   //   회원 존재 여부를 확인하는 메소드
   boolean isUserExists(String user_name, String mobile) throws SQLException;

   //   로그인시 access_level 이 0인지 1인지 알아오는 메소드(관리자인지 일반회원인지 확인)
   int getAccessLevelByUserId(String user_id) throws SQLException;

   //   인증번호 일치 시 휴면 해제 처리(is_active=0)
   boolean updateUserIsActive(String login_ip, String sessionUser_id, String sessionuser_name, String sessionMobile) throws SQLException;

   //   유저 총 결제금액·포인트 보정 및 등급 업데이트
   void processPostPayment(String user_id, int finalPay, int used_point, int earned_point) throws Exception;

   //   90일 뒤에 비밀번호 변경
   void updateLastPwdUpdate(String user_id) throws SQLException;

   //   회원 목록 조회 메서드
   List<MemberVO> getMemberList(String searchType, String searchWord, int offset, int limit) throws Exception;
   
   //   회원 총 개수 조회 메서드
   int getMemberTotalCount(String searchType, String searchWord) throws SQLException;

   //   user_id로 회원상세정보 가져오는 메서드
   MemberVO getMemberByUserId(String user_id) throws SQLException;

   //   등급을 기반으로 적립률 계산
   int getEarnedPoint(String user_id, int finalPay) throws SQLException;

   //   결제 시점의 누적 결제금액을 조회
   int getCurrentTotalPayment(String user_id) throws SQLException;

   //	유저 정보 결제전으로 원복
   void rollbackUserPointsAndTotalPayment(String user_id, int used_point, int earned_point, int paid_amount, int total_payment_stamp) throws SQLException;

   //	유저 등급 원복
   void recalcUserGrade(String user_id) throws SQLException;
	
	// 유저의 아이디로 등급을 알아오는 메소드 
	String selectuserGrade(String userid) throws SQLException;

	// 유저의 포인트 증감내역을 보여주는 메소드 
	List<Map<String, Object>> getPointHistory(String userid, int pageSize, int offset) throws SQLException;

	// 유저 포인트 증감 총내역 개수
	int getPointListTotalCount(String userid) throws SQLException;

	//	입력한 비밀번호가 해당 유저의 비밀번호와 일치할 경우 탈퇴여부 변경
	int deletecomple(String user_id) throws SQLException;
}








