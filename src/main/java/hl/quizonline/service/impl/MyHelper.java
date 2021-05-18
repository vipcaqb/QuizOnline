package hl.quizonline.service.impl;

import java.awt.Color;
import java.util.Random;

import org.springframework.context.annotation.Bean;

public class MyHelper {
	public MyHelper() {
	}
	
	public static String getTag(String filename) {
		for(int i =filename.length()-1 ; i>=0;i--) {
			if(filename.charAt(i)=='.') {
				return filename.substring(i);
			}
		}
		return "";
	}
	
	public String getRandomHexColor() {
		Random rand = new Random();
		float r = rand. nextFloat();
		float g = rand. nextFloat();
		float b = rand. nextFloat();
		Color randomColor = new Color(r, g, b);
		String hex = "#"+Integer.toHexString(randomColor.getRGB()).substring(2);
		return hex;
	}
}
