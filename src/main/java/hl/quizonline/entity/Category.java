package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

import java.util.List;


/**
 * The persistent class for the Category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int categoryID;

	@Column
	@Nationalized
	private String categoryName;

	//bi-directional many-to-many association to ExamPackage
	@ManyToMany(mappedBy="categories")
	private List<ExamPackage> examPackages;

	public Category() {
	}

	public int getCategoryID() {
		return this.categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ExamPackage> getExamPackages() {
		return this.examPackages;
	}

	public void setExamPackages(List<ExamPackage> examPackages) {
		this.examPackages = examPackages;
	}

	public Category(int categoryID, String categoryName, List<ExamPackage> examPackages) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.examPackages = examPackages;
	}

	public Category(String categoryName, List<ExamPackage> examPackages) {
		super();
		this.categoryName = categoryName;
		this.examPackages = examPackages;
	}

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

}