package hl.quizonline.model;

import java.util.List;

public class LineChartModel {
	int year;
	List<Long> viewsList;
	long viewSumary;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Long> getViewsList() {
		return viewsList;
	}
	public void setViewsList(List<Long> viewsList) {
		this.viewsList = viewsList;
	}
	public long getViewSumary() {
		return viewSumary;
	}
	public void setViewSumary(long viewSumary) {
		this.viewSumary = viewSumary;
	}
	
	public String getJSList() {
		if(viewsList!= null) {
			String jsList = "";
			
			for(int i = 0 ; i<viewsList.size();i++) {
				if(i==0) {
					if(viewsList.get(i)>=0)
						jsList += +viewsList.get(i);
				}
				else {
					if(viewsList.get(i)>=0)
						jsList += ","+viewsList.get(i);
				}
			}
			return jsList;
		}
		else {
			return "[]";
		}
	}
}
