package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.CategoryVO;
import myshop.domain.ImageVO;

public interface ProductDAO {

	//	메인페이지에 보여지는 상품이미지파일명을 모두 조회(select)하는 메소드
	List<ImageVO> imageSelectAll() throws SQLException;

	//	숙박업소 카테고리 정보를 얻어오는 메소드
	List<CategoryVO> getCategoryList() throws SQLException;
	
}
