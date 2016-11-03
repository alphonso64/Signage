package com.thingword.alphonso.bean.db;

public class Product {
	private int ID;
	private String invcode;
	private String path;
	private String pdfpath;
	
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPdfpath() {
		return pdfpath;
	}
	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

}
