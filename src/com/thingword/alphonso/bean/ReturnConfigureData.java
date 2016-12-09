package com.thingword.alphonso.bean;

import java.util.List;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.bean.db.VideoConfigure;

public class ReturnConfigureData<T> {
	private String return_msg;
	private String return_code;
	private Configure pdfconfigure;
 	private List<T> pdf;
	private VideoConfigure videoconfigure;
 	private List<T> video;
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public Configure getPdfconfigure() {
		return pdfconfigure;
	}
	public void setPdfconfigure(Configure pdfconfigure) {
		this.pdfconfigure = pdfconfigure;
	}
	public List<T> getPdf() {
		return pdf;
	}
	public void setPdf(List<T> pdf) {
		this.pdf = pdf;
	}
	public VideoConfigure getVideoconfigure() {
		return videoconfigure;
	}
	public void setVideoconfigure(VideoConfigure videoconfigure) {
		this.videoconfigure = videoconfigure;
	}
	public List<T> getVideo() {
		return video;
	}
	public void setVideo(List<T> video) {
		this.video = video;
	}
}
