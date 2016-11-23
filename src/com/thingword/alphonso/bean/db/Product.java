package com.thingword.alphonso.bean.db;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Product {
	private int ID;
	private String invcode;
	private String path;
	private String pdfpath;
	private String invstd;
	
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
	
		this.invcode = invcode;
	}
	@JsonIgnore
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@JsonIgnore
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	@JsonIgnore
	public String getPdfpath() {
		return pdfpath;
	}
	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}
	public String getInvstd() {
		return invstd;
	}
	public void setInvstd(String invstd) {
		this.invstd = invstd;
	}

}
