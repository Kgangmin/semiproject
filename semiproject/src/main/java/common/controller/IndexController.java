package common.controller;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.ImageVO;
import myshop.model.ProductDAO;
import myshop.model.ProductDAO_imple;

public class IndexController extends AbstractController {

	private ProductDAO pdao;
	
	public IndexController() {
		pdao = new ProductDAO_imple();
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			List<ImageVO> imgList = pdao.imageSelectAll();
			
			/*
			   for(ImageVO imgvo : imgList) {
			   System.out.println("imgno : "+imgvo.getImgno()+", imgname : "+imgvo.
			   getImgname()+", imgfilename : "+imgvo.getImgfilename()); }
			 */
			/*
				imgno : 1, imgname : 미샤, imgfilename : 미샤.png
				imgno : 2, imgname : 원더플레이스, imgfilename : 원더플레이스.png
				imgno : 3, imgname : 레노보, imgfilename : 레노보.png
				imgno : 4, imgname : 동원, imgfilename : 동원.png
			*/
			
			request.setAttribute("imgList", imgList);
			
			super.setRedirect(false); 
			super.setViewPage("/WEB-INF/index.jsp"); 
		} catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true); 
			super.setViewPage(request.getContextPath() +"/error.hb"); 
			
		}
		
		
	}

}
