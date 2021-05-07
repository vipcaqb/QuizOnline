package hl.quizonline.service.impl;

public class MyHelper {
	public static String getTag(String filename) {
		for(int i =filename.length()-1 ; i>=0;i--) {
			if(filename.charAt(i)=='.') {
				return filename.substring(i);
			}
		}
		return "";
	}
}
