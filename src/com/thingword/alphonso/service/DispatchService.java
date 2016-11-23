package com.thingword.alphonso.service;

import java.io.FileInputStream;

import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.ReturnConfigureData;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.db.Configure;

public interface DispatchService {
	public byte[] getDispatchFile(String line,String location);
	public ReturnConfigureData<DispatchFile> getDispatchFileDetail(String invcode);
	public ReturnMessage setConfigure(Configure configure);
	public ReturnMessage setSpecificConfigure(Configure configure);
}
