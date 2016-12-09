package com.thingword.alphonso.bean;

public class DisPatchMessage {
	private String pdf;
	private String video;
	private String pdfInterval;
	private String videoInterval;
	
	public String getPdfInterval() {
		return pdfInterval;
	}
	public void setPdfInterval(String pdfInterval) {
		this.pdfInterval = pdfInterval;
	}
	public String getVideoInterval() {
		return videoInterval;
	}
	public void setVideoInterval(String videoInterval) {
		this.videoInterval = videoInterval;
	}
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
}
