package member.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberVO;

public interface MemberDAO {


	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다) 
		boolean idDuplicateCheck(String userid) throws SQLException;


	// 이메일 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)  

	boolean emailDuplicateCheck2(Map<String, String> paraMap) throws SQLException;

	boolean emailDuplicateCheck(String email) throws SQLException;

	// 로그인	처리
	MemberVO login(Map<String, String> paraMap) throws SQLException;

	// 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
	int registerMember(MemberVO member) throws SQLException;

	// 패스워드가 맞는지 확인 하는 메소드 
	boolean pwdcheck(Map<String, String> paramap) throws SQLException;

	//유저의 이메일을 변경하는 메소드 
	int changeEmailMember(Map<String, String> paramap) throws SQLException;
	

	

	
	
	
	
}








