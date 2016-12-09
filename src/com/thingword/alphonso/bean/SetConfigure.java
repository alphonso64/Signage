package com.thingword.alphonso.bean;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.VideoConfigure;

public class SetConfigure {
	private Configure pdf;
	private VideoConfigure video;
	public Configure getPdf() {
		return pdf;
	}
	public void setPdf(Configure pdf) {
		this.pdf = pdf;
	}
	public VideoConfigure getVideo() {
		return video;
	}
	public void setVideo(VideoConfigure video) {
		this.video = video;
	}
}
