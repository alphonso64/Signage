package com.thingword.alphonso.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.WebSocketWorker;
import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.MESSAGE;
import com.thingword.alphonso.bean.ReturnConfigureData;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.dao.impl.ConfigureDaoImpl;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.dao.impl.RmLineDaoImpl;
import com.thingword.alphonso.service.DispatchService;
import com.thingword.alphonso.util.FileUtil;

public class DispatchServiceImpl implements DispatchService {
	@Autowired
	RmLineDaoImpl rmLineDaoImpl;
	@Autowired
	ConfigureDaoImpl configureDaoImpl;
	@Autowired
	ProductDaoImpl productDaoImpl;

	public byte[] getDispatchFile(String line, String location) {
		FileInputStream fis = null;
		String filePath = rmLineDaoImpl.getPath(line, location);
		//System.out.println("filePath" + filePath);
		byte[] bytes = new byte[0];
		if (filePath == null)
			return bytes;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bytes = new byte[fis.available()];
			fis.read(bytes);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public ReturnMessage dispatchFileTosWorker(String line, String invcode) {
		ReturnMessage message = new ReturnMessage();
		message.setReturn_code(MESSAGE.RETURN_FAIL);
		message.setReturn_msg(MESSAGE.DISPATCH_NORECORD);
		Configure configure = configureDaoImpl.getConfigure(invcode);
		RmLine rmLine = rmLineDaoImpl.getRmline(line);
		if (rmLine != null && configure != null) {
			rmLine.setW1(configure.getW1());
			rmLine.setW2(configure.getW2());
			rmLine.setW3(configure.getW3());
			rmLine.setW4(configure.getW4());
			rmLine.setW5(configure.getW5());
			rmLine.setW6(configure.getW6());
			rmLine.setW7(configure.getW7());
			rmLine.setW8(configure.getW8());
			rmLine.setW9(configure.getW9());
			rmLine.setW10(configure.getW10());
			rmLine.setW11(configure.getW11());
			rmLine.setW12(configure.getW12());
			rmLine.setW13(configure.getW13());
			rmLine.setW14(configure.getW14());
			rmLine.setW15(configure.getW15());
			rmLine.setW16(configure.getW16());
			rmLine.setW17(configure.getW17());
		}else{
//			System.out.println("flase");
			return message;
		}
		boolean flag = rmLineDaoImpl.updateRmLine(rmLine);
//		System.out.println("updateRmLine:"+flag+" "+rmLine.getW1());

		for (WebSocketWorker item : WebSocketWorker.webSocketSet) {
			try {
			
				if (item.ID != null && item.ID.getProductionline()!=null) {
					if (item.ID.getProductionline().equals(line.toUpperCase())) {
						item.sendMessage(" ");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.setReturn_code(MESSAGE.DISPATCH__DISERR);
			}
		}
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.DISPATCH_SUCCESS);
		return message;

	}

	@Override
	public ReturnConfigureData<DispatchFile> getDispatchFileDetail(String invcode) {
		ReturnConfigureData<DispatchFile> returnData = new ReturnConfigureData<>();
		returnData.setReturn_code(MESSAGE.RETURN_FAIL);
		returnData.setReturn_msg(MESSAGE.QUERY_DISPATCH_NORECORD);
		Product product = productDaoImpl.getProduct(invcode);
		if(product == null){
			return returnData;
		}
		List<DispatchFile> dispatchFiles = FileUtil.getPdfFileList(product.getPdfpath());
		if(dispatchFiles.isEmpty()){
			return returnData;
		}
		Configure configure = configureDaoImpl.getConfigure(invcode).reducePath();
//		String val = configure.getW1();
//		System.out.println(val.substring(val.lastIndexOf("\\")+1,val.length()));
		returnData.setData(dispatchFiles);
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		returnData.setReturn_msg(MESSAGE.QUERY_DISPATCH__SUCCESS);
		returnData.setConfigure(configure);
		return returnData;
	}

	@Override
	public ReturnMessage setConfigure(Configure configure) {
		ReturnMessage message = new ReturnMessage();
		message.setReturn_code(MESSAGE.RETURN_FAIL);
		message.setReturn_msg(MESSAGE.CONFIGURE__FAIL);
		Configure configure_id = configureDaoImpl.getConfigure(configure.getInvcode());
		if(configure_id== null)
			return message;
		
		configure.setID(configure_id.getID());
		configureDaoImpl.updateConfigure(configure);
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.CONFIGURE__SUCCESS);
		return message;
	}

	@Override
	public ReturnMessage setSpecificConfigure(Configure configure) {
		ReturnMessage message = new ReturnMessage();
		message.setReturn_code(MESSAGE.RETURN_FAIL);
		message.setReturn_msg(MESSAGE.CONFIGURE__FAIL);
		Configure configure_id = configureDaoImpl.getConfigure(configure.getInvcode());
		if(configure_id== null)
			return message;
		
		String temp = configure.getW1();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW1(null);
			}else{
				configure_id.setW1(temp);
			}
		}
		
		temp = configure.getW2();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW2(null);
			}else{
				configure_id.setW2(temp);
			}
		}
		
		temp = configure.getW3();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW3(null);
			}else{
				configure_id.setW3(temp);
			}
		}
		
		temp = configure.getW4();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW4(null);
			}else{
				configure_id.setW4(temp);
			}
		}
		
		temp = configure.getW5();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW5(null);
			}else{
				configure_id.setW5(temp);
			}
		}
		
		temp = configure.getW6();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW6(null);
			}else{
				configure_id.setW6(temp);
			}
		}
		
		temp = configure.getW7();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW7(null);
			}else{
				configure_id.setW7(temp);
			}
		}
		
		temp = configure.getW8();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW8(null);
			}else{
				configure_id.setW8(temp);
			}
		}
		
		temp = configure.getW9();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW9(null);
			}else{
				configure_id.setW9(temp);
			}
		}
		
		temp = configure.getW10();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW10(null);
			}else{
				configure_id.setW10(temp);
			}
		}
		
		temp = configure.getW11();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW11(null);
			}else{
				configure_id.setW11(temp);
			}
		}
		
		temp = configure.getW12();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW12(null);
			}else{
				configure_id.setW12(temp);
			}
		}
		
		temp = configure.getW13();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW13(null);
			}else{
				configure_id.setW13(temp);
			}
		}
		
		temp = configure.getW14();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW14(null);
			}else{
				configure_id.setW14(temp);
			}
		}
		
		temp = configure.getW15();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW15(null);
			}else{
				configure_id.setW15(temp);
			}
		}
		
		temp = configure.getW16();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW16(null);
			}else{
				configure_id.setW16(temp);
			}
		}
		
		temp = configure.getW17();
		if(temp!=null){
			if(temp.equals("null")){
				configure_id.setW17(null);
			}else{
				configure_id.setW17(temp);
			}
		}
		
		configureDaoImpl.updateConfigure(configure_id);
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.CONFIGURE__SUCCESS);
		return message;
	}

}
