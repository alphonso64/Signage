package com.thingword.alphonso.bean;

import java.util.List;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.RmLine;

public class ReturnConfigureData<T> {
	private String return_msg;
	private String return_code;
	private Configure configure;
 	private List<T> data;
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
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Configure getConfigure() {
		return configure;
	}
	public void setConfigure(Configure configure) {
		this.configure = configure;
	}

}
