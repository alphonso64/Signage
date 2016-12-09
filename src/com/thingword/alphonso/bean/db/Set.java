package com.thingword.alphonso.bean.db;

public class Set {
	private int ID;
	private String pdfinterval;
	private String videointerval;
	private String msginterval;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPdfinterval() {
		return pdfinterval;
	}
	public void setPdfinterval(String pdfinterval) {
		this.pdfinterval = pdfinterval;
	}
	public String getVideointerval() {
		return videointerval;
	}
	public void setVideointerval(String videointerval) {
		this.videointerval = videointerval;
	}
	public String getMsginterval() {
		return msginterval;
	}
	public void setMsginterval(String msginterval) {
		this.msginterval = msginterval;
	}
}
