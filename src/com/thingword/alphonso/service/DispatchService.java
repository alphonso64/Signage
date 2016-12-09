package com.thingword.alphonso.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.core.StreamingOutput;

import com.thingword.alphonso.bean.DisPatchMessage;
import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.ReturnConfigureData;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.SetConfigure;
import com.thingword.alphonso.bean.db.Configure;

public interface DispatchService {
	public byte[] getDispatchFile(String line,String location);
	public byte[] getVideoDispatchFile(String line, String location) ;
	public StreamingOutput getVideoDispatchFileStream(String line, String location);
	public String getVideoDispatchFilePath(String line, String location);
	public ReturnConfigureData<DispatchFile> getDispatchFileDetail(String invcode);
	public ReturnMessage setConfigure(SetConfigure configure);
	public ReturnMessage setSpecificConfigure(SetConfigure configure);
	public DisPatchMessage getCurrentState(String line,String location);
}
