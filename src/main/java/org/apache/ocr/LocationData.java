package org.apache.ocr;

public class LocationData {
	private String word;
	private int box_x1;
	private int box_x2;
	private int box_y1;
	private int box_y2;
	
	
	
	public LocationData(String word, int box_x1, int box_y1,int box_x2,
			int box_y2) {
		super();
		this.word = word;
		this.box_x1 = box_x1;
		this.box_x2 = box_x2;
		this.box_y1 = box_y1;
		this.box_y2 = box_y2;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getBox_x1() {
		return box_x1;
	}
	public void setBox_x1(int box_x1) {
		this.box_x1 = box_x1;
	}
	public int getBox_x2() {
		return box_x2;
	}
	public void setBox_x2(int box_x2) {
		this.box_x2 = box_x2;
	}
	public int getBox_y1() {
		return box_y1;
	}
	public void setBox_y1(int box_y1) {
		this.box_y1 = box_y1;
	}
	public int getBox_y2() {
		return box_y2;
	}
	public void setBox_y2(int box_y2) {
		this.box_y2 = box_y2;
	}
	
	
}
