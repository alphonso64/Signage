package com.thingword.alphonso.service;

import java.io.InputStream;

import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;

public interface UploadService {
	public ReturnMessage uploadProductCraftResource(String name,InputStream inputStream);
	
	public ReturnMessage uploadUniversalVideoesource(String name,InputStream inputStream);
	
	public ReturnData<DispatchFile> getUniversalVideoList();
}
