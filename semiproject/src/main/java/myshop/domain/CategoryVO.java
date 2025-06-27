package myshop.domain;

public class CategoryVO {

	private String stay_category_no;
	private String stay_category_name;
	
	public CategoryVO() { }
	
	public CategoryVO(String stay_category_no, String stay_category_name) {
		this.stay_category_no = stay_category_no;
		this.stay_category_name = stay_category_name;
	}

	public String getStay_category_no() {
		return stay_category_no;
	}

	public void setStay_category_no(String stay_category_no) {
		this.stay_category_no = stay_category_no;
	}

	public String getStay_category_name() {
		return stay_category_name;
	}

	public void setStay_category_name(String stay_category_name) {
		this.stay_category_name = stay_category_name;
	}	
}
