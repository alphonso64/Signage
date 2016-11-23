package com.thingword.alphonso.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheableOperation;

import com.thingword.alphonso.bean.MESSAGE;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.dao.ConfigureDao;
import com.thingword.alphonso.dao.impl.ConfigureDaoImpl;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.service.UploadService;
import com.thingword.alphonso.util.FileUtil;
import com.thingword.alphonso.util.ZipUtil;

public class UploadServiceImpl implements UploadService {
	
	public static String[] keyList = new String[]{"插件","整焊","ICT","外观","检测","包装"};

	public static final HashSet<String> keyWords = new HashSet<String>() {
		{
			add(keyList[0]);
			add(keyList[1]);
			add(keyList[2]);
			add(keyList[3]);
			add(keyList[4]);
			add(keyList[5]);
		}
	};

	@Autowired
	private ProductDaoImpl productDaoImpl;
	@Autowired
	private ConfigureDaoImpl configureDaoImpl;

	private static final String prePath = "D:\\upload\\";
	private static final String tempPath = "temp\\";
	private static final String pdfPath = "pdf\\";

//	public ReturnMessage uploadProductCraftResource(String name, InputStream inputStream) {
//		ReturnMessage returnMessage = new ReturnMessage();
//		returnMessage.setReturn_code(MESSAGE.RETURN_FAIL);
//		returnMessage.setReturn_msg(MESSAGE.UPLOAD_FIAL);
//		String parent = parseProductCraftFileName(name);
//		if (parent == null) {
//			returnMessage.setReturn_msg(MESSAGE.UPLOAD_FAIL_FORMAT);
//			return returnMessage;
//		}
//		Product product = new Product();
//		
//		product.setInvcode(parent);
//		product.setPath(prePath + parent);
//		if (!ZipUtil.unzip(inputStream, prePath + parent)) {
//			returnMessage.setReturn_msg(MESSAGE.UPLOAD_FAIL_UNZIP);
//			return returnMessage;
//		}
//		HashMap<String, List<String>> files = parseXlsFile(prePath + parent, product);
//		Configure configure = parsePdfFiles(files);
//		configure.setInvcode(parent);
//		product.setPdfpath(prePath + parent + "\\" + pdfPath);
//		productDaoImpl.updateProduct(product);
//		configureDaoImpl.updateConfigure(configure);
//		returnMessage.setReturn_code(MESSAGE.RETURN_SUCCESS);
//		returnMessage.setReturn_msg(MESSAGE.UPLOAD_SUCCESS);
//		return returnMessage;
//	};
	
	public ReturnMessage uploadProductCraftResource(String name, InputStream inputStream) {
		ReturnMessage returnMessage = new ReturnMessage();
		returnMessage.setReturn_code(MESSAGE.RETURN_FAIL);
		returnMessage.setReturn_msg(MESSAGE.UPLOAD_FIAL);
		Product product = parseProductCraftFileName(name);
		String parent = product.getInvcode();
		if (parent == null) {
			returnMessage.setReturn_msg(MESSAGE.UPLOAD_FAIL_FORMAT);
			return returnMessage;
		}
		product.setPath(prePath + parent);
		if (!ZipUtil.unzip(inputStream, prePath + parent)) {
			returnMessage.setReturn_msg(MESSAGE.UPLOAD_FAIL_UNZIP);
			return returnMessage;
		}
//		HashMap<String, List<String>> files = parseXlsFile(prePath + parent, product);
		
		HashMap<String, List<String>> files = parseALLpdfFile(prePath + parent, product);
		Configure configure = parsePdfFiles(files);
		configure.setInvcode(parent);
		product.setPdfpath(prePath + parent);
		productDaoImpl.updateProduct(product);
		configureDaoImpl.updateConfigure(configure);
		returnMessage.setReturn_code(MESSAGE.RETURN_SUCCESS);
		returnMessage.setReturn_msg(MESSAGE.UPLOAD_SUCCESS);
		return returnMessage;
	};

	private Configure parsePdfFiles(HashMap<String, List<String>> files) {
		//System.out.println("parsePdfFiles");
		Configure configure= new Configure();
		List<String> backlist = new ArrayList<>();		
		if(files.containsKey(keyList[0])){
			List<String> ls = files.get(keyList[0]);
			int len = Math.min(8, ls.size());
			for(int i =0 ;i<len;i++){
				if(i==0){
					configure.setW1(ls.get(i));
				}else if(i==1){
					configure.setW2(ls.get(i));
				}else if(i==2){
					configure.setW3(ls.get(i));
				}else if(i==3){
					configure.setW4(ls.get(i));
				}else if(i==4){
					configure.setW5(ls.get(i));
				}else if(i==5){
					configure.setW6(ls.get(i));
				}else if(i==6){
					configure.setW7(ls.get(i));
				}else if(i==7){
					configure.setW8(ls.get(i));
				}
			}
		}
		for(int i=1;i<keyList.length;i++){
			if(files.containsKey(keyList[i])){
				backlist.addAll(files.get(keyList[i]));
			}
		}
		int len = Math.min(9, backlist.size());
		for(int i =0 ;i< len;i++){
			//System.out.println(backlist.get(i));
			if(i==0){
				configure.setW9(backlist.get(i));
			}else if(i==1){
				configure.setW10(backlist.get(i));
			}else if(i==2){
				configure.setW11(backlist.get(i));
			}else if(i==3){
				configure.setW12(backlist.get(i));
			}else if(i==4){
				configure.setW13(backlist.get(i));
			}else if(i==5){
				configure.setW14(backlist.get(i));
			}else if(i==6){
				configure.setW15(backlist.get(i));
			}else if(i==7){
				configure.setW16(backlist.get(i));
			}else if(i==8){
				configure.setW17(backlist.get(i));
			}
		}
		return configure;
	}
	
	private HashMap<String, List<String>> parseALLpdfFile(String parent, Product product){
		List<File> ls = FileUtil.getpdfFileList(parent);
		HashMap<String, String> tempMap = new HashMap<>();
		HashMap<String, List<String>> val = new HashMap<>();
		for(File file:ls){
			String name = file.getName();
			name = name.substring(0,name.length()-4);
			if(keyWords.contains(name)){
				tempMap.put(name,file.getAbsolutePath());
			}else{
				file.delete();
			}	
		}
		val = FileUtil.pdfSplitter(tempMap);
		return val;
	}
	
	private HashMap<String, List<String>> parseXlsFile(String parent, Product product) {
		List<File> ls = FileUtil.getXlsFileList(parent);
		File fileTemp = new File(parent + "\\" + tempPath);
		fileTemp.mkdir();
		File filePDF = new File(parent + "\\" + pdfPath);
		filePDF.mkdir();
		List<String> inList = new ArrayList<>();
		List<String> outTempList = new ArrayList<>();
		HashMap<String, String> tempMap = new HashMap<>();
		HashMap<String, List<String>> val = new HashMap<>();
		for (File file : ls) {
			try {
				InputStream inp = new FileInputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook(inp);
				int len = wb.getNumberOfSheets();
				wb.close();
				inp.close();
				//System.out.println("save before:" + len);
				for (int i = 0; i < len; i++) {
					inp = new FileInputStream(file);
					wb = new HSSFWorkbook(inp);
					String name = wb.getSheetAt(i).getSheetName();
					//System.out.println("save before:" + name);
					if (keyWords.contains(name)) {
						String xlspath = parent + "\\" + tempPath + name + ".xls";
						String pdfpath = parent + "\\" + pdfPath + name + ".pdf";
						//System.out.println("xlspath pdfpath:" + xlspath+" "+pdfpath);
						int j;
						for (j = 0; j < i; j++) {
							wb.removeSheetAt(0);
						}
						for (; j < len - 1; j++) {
							wb.removeSheetAt(1);
						}
						inList.add(xlspath);
						tempMap.put(name, pdfpath);
						outTempList.add(pdfpath);
						FileOutputStream output = new FileOutputStream(xlspath);
						wb.write(output);
						output.close();
						wb.close();
						inp.close();
						//System.out.println("save after:" + pdfpath);
					} else {
						wb.close();
						inp.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			FileUtil.xlsToPdf(inList, outTempList);
			val = FileUtil.pdfSplitter(tempMap);
			FileUtil.deleteAllFilesOfDir(fileTemp);
		}
		return val;
	}

	private Product parseProductCraftFileName(String name) {
		Product product = new Product();
		try {
			String pname = new String(name.getBytes("ISO8859-1"), "utf-8");
			String regex = "[0-9A-Za-z]+\\(.+\\)\\.zip";
			String regex_a = "[0-9A-Za-z]+.zip";
			Pattern p1 = Pattern.compile(regex_a);
			Pattern p2 = Pattern.compile(regex);
			if (p1.matcher(pname).matches()) {
				product.setInvcode(pname.substring(0, pname.length() - 4));
			}else if(p2.matcher(pname).matches()){
				product.setInvcode(pname.substring(0,pname.indexOf("(")));
				product.setInvstd(pname.substring(pname.indexOf("(")+1,pname.indexOf(")")));
			}
		} catch (UnsupportedEncodingException e) {
		}
		return product;
	}

}
