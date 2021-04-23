package hl.quizonline.model;

public class CategoryModel {
	
	private int categoryID;
	private String categoryName;
	private boolean selected;
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public CategoryModel(int categoryID, String categoryName, boolean selected) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.selected = selected;
	}
	@Override
	public String toString() {
		return "CategoryModel [categoryID=" + categoryID + ", categoryName=" + categoryName + ", selected=" + selected
				+ "]";
	}
	
	
}
