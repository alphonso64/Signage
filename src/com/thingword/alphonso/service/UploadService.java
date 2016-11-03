package com.thingword.alphonso.service;

import java.io.InputStream;

import com.thingword.alphonso.bean.ReturnMessage;

public interface UploadService {
	public ReturnMessage uploadProductCraftResource(String name,InputStream inputStream);;
}
