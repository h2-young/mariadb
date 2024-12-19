package bookmall.vo;

public class CategoryVo {
	private int no;
	private String category;
	
	public CategoryVo(String category) {
		this.category = category;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
