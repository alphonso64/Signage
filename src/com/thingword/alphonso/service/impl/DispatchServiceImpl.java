package com.thingword.alphonso.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.metadata.IIOMetadataFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.thingword.alphonso.WebSocketWorker;
import com.thingword.alphonso.bean.DisPatchMessage;
import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.MESSAGE;
import com.thingword.alphonso.bean.ReturnConfigureData;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.SetConfigure;
import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.bean.db.Set;
import com.thingword.alphonso.bean.db.VideoConfigure;
import com.thingword.alphonso.bean.db.VideoRmLine;
import com.thingword.alphonso.dao.impl.ConfigureDaoImpl;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.dao.impl.RmLineDaoImpl;
import com.thingword.alphonso.service.DispatchService;
import com.thingword.alphonso.util.CLog;
import com.thingword.alphonso.util.FileUtil;

public class DispatchServiceImpl implements DispatchService {
	@Autowired
	RmLineDaoImpl rmLineDaoImpl;
	@Autowired
	ConfigureDaoImpl configureDaoImpl;
	@Autowired
	ProductDaoImpl productDaoImpl;
	
	private static final String pdfInterval = "20";
	private static final String videoInterval = "4";
	
	

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
	
	public byte[] getVideoDispatchFile(String line, String location) {
		FileInputStream fis = null;
		String filePath = rmLineDaoImpl.getVideoath(line, location);
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
	
	@Override
	public StreamingOutput getVideoDispatchFileStream(String line, String location) {
		String filePath = rmLineDaoImpl.getVideoath(line, location);
		File file = new File(filePath);
        StreamingOutput stream = null;
        try {
	        final InputStream in = new FileInputStream(file);
	        stream = new StreamingOutput() {
	            public void write(OutputStream out) throws IOException, WebApplicationException {
	                try {
	                    int read = 0;
	                        byte[] bytes = new byte[1024];
	
	                        while ((read = in.read(bytes)) != -1) {
	                            out.write(bytes, 0, read);
	                        }
	                } catch (Exception e) {
	                    throw new WebApplicationException(e);
	                }
	            }
	        };
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		return stream;
	}
	
	@Override
	public String getVideoDispatchFilePath(String line, String location) {
		return rmLineDaoImpl.getVideoath(line, location);
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
			return message;
		}
		boolean flag = rmLineDaoImpl.updateRmLine(rmLine);
		
		VideoConfigure videoconfigure = configureDaoImpl.getVideoConfigure(invcode);
		VideoRmLine videormLine = rmLineDaoImpl.getVideoRmline(line);
		if (videormLine != null && videoconfigure != null) {
			videormLine.setW1(videoconfigure.getW1());
			videormLine.setW2(videoconfigure.getW2());
			videormLine.setW3(videoconfigure.getW3());
			videormLine.setW4(videoconfigure.getW4());
			videormLine.setW5(videoconfigure.getW5());
			videormLine.setW6(videoconfigure.getW6());
			videormLine.setW7(videoconfigure.getW7());
			videormLine.setW8(videoconfigure.getW8());
			videormLine.setW9(videoconfigure.getW9());
			videormLine.setW10(videoconfigure.getW10());
			videormLine.setW11(videoconfigure.getW11());
			videormLine.setW12(videoconfigure.getW12());
			videormLine.setW13(videoconfigure.getW13());
			videormLine.setW14(videoconfigure.getW14());
			videormLine.setW15(videoconfigure.getW15());
			videormLine.setW16(videoconfigure.getW16());
			videormLine.setW17(videoconfigure.getW17());
			rmLineDaoImpl.updateVideoRmLine(videormLine);
		}
		for (WebSocketWorker item : WebSocketWorker.webSocketSet) {
			try {		
				if (item.ID != null && item.ID.getProductionline()!=null && item.ID.getStation()!=null) {
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
		
		List<DispatchFile> videodispatchFiles = FileUtil.getVideoFileList(product.getVideopath());
		
		VideoConfigure videoconfigure = configureDaoImpl.getVideoConfigure(invcode).reducePath();
		returnData.setVideo(videodispatchFiles);
		returnData.setVideoconfigure(videoconfigure);
			
		returnData.setReturn_code(MESSAGE.RETURN_SUCCESS);
		returnData.setReturn_msg(MESSAGE.QUERY_DISPATCH__SUCCESS);
		returnData.setPdf(dispatchFiles);
		returnData.setPdfconfigure(configure);
		return returnData;
	}

	@Override
	public ReturnMessage setConfigure(SetConfigure setconfigure) {
		ReturnMessage message = new ReturnMessage();
		message.setReturn_code(MESSAGE.RETURN_FAIL);
		message.setReturn_msg(MESSAGE.CONFIGURE__FAIL);
		Configure configure = setconfigure.getPdf();
		Configure configure_id = configureDaoImpl.getConfigure(configure.getInvcode());
		if(configure_id== null)
			return message;	
		configure.setID(configure_id.getID());
		configureDaoImpl.updateConfigure(configure);
		
		VideoConfigure videoConfigure = setconfigure.getVideo();
		VideoConfigure videoconfigure_id = configureDaoImpl.getVideoConfigure(videoConfigure.getInvcode());
		if(videoconfigure_id!= null)
		{
			videoConfigure.setID(configure_id.getID());
			configureDaoImpl.updateVideoConfigure(videoConfigure);
		}
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.CONFIGURE__SUCCESS);
		return message;
	}

	@Override
	public ReturnMessage setSpecificConfigure(SetConfigure setconfigure) {
		ReturnMessage message = new ReturnMessage();
		message.setReturn_code(MESSAGE.RETURN_FAIL);
		message.setReturn_msg(MESSAGE.CONFIGURE__FAIL);
		Configure configure = setconfigure.getPdf();
		Configure configure_id = configureDaoImpl.getConfigure(configure.getInvcode());
		if(configure_id== null)
			return message;
		
		String temp = configure.getW1();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW1(null);
			}else{
				configure_id.setW1(temp);
			}
		}
		
		temp = configure.getW2();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW2(null);
			}else{
				configure_id.setW2(temp);
			}
		}
		
		temp = configure.getW3();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW3(null);
			}else{
				configure_id.setW3(temp);
			}
		}
		
		temp = configure.getW4();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW4(null);
			}else{
				configure_id.setW4(temp);
			}
		}
		
		temp = configure.getW5();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW5(null);
			}else{
				configure_id.setW5(temp);
			}
		}
		
		temp = configure.getW6();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW6(null);
			}else{
				configure_id.setW6(temp);
			}
		}
		
		temp = configure.getW7();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW7(null);
			}else{
				configure_id.setW7(temp);
			}
		}
		
		temp = configure.getW8();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW8(null);
			}else{
				configure_id.setW8(temp);
			}
		}
		
		temp = configure.getW9();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW9(null);
			}else{
				configure_id.setW9(temp);
			}
		}
		
		temp = configure.getW10();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW10(null);
			}else{
				configure_id.setW10(temp);
			}
		}
		
		temp = configure.getW11();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW11(null);
			}else{
				configure_id.setW11(temp);
			}
		}
		
		temp = configure.getW12();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW12(null);
			}else{
				configure_id.setW12(temp);
			}
		}
		
		temp = configure.getW13();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW13(null);
			}else{
				configure_id.setW13(temp);
			}
		}
		
		temp = configure.getW14();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW14(null);
			}else{
				configure_id.setW14(temp);
			}
		}
		
		temp = configure.getW15();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW15(null);
			}else{
				configure_id.setW15(temp);
			}
		}
		
		temp = configure.getW16();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW16(null);
			}else{
				configure_id.setW16(temp);
			}
		}
		
		temp = configure.getW17();
		if(temp!=null){
			if(temp.equals("empty")){
				configure_id.setW17(null);
			}else{
				configure_id.setW17(temp);
			}
		}
		configureDaoImpl.updateConfigure(configure_id);
		
		VideoConfigure videoconfigure = setconfigure.getVideo();
		VideoConfigure videoconfigure_id = configureDaoImpl.getVideoConfigure(videoconfigure.getInvcode());
		if(videoconfigure_id != null){
			temp = videoconfigure.getW1();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW1(null);
				}else{
					videoconfigure_id.setW1(temp);
				}
			}
			
			temp = videoconfigure.getW2();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW2(null);
				}else{
					videoconfigure_id.setW2(temp);
				}
			}
			
			temp = videoconfigure.getW3();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW3(null);
				}else{
					videoconfigure_id.setW3(temp);
				}
			}
			
			temp = videoconfigure.getW4();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW4(null);
				}else{
					videoconfigure_id.setW4(temp);
				}
			}
			
			temp = videoconfigure.getW5();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW5(null);
				}else{
					videoconfigure_id.setW5(temp);
				}
			}
			
			temp = videoconfigure.getW6();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW6(null);
				}else{
					videoconfigure_id.setW6(temp);
				}
			}
			
			temp = videoconfigure.getW7();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW7(null);
				}else{
					videoconfigure_id.setW7(temp);
				}
			}
			
			temp = videoconfigure.getW8();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW8(null);
				}else{
					videoconfigure_id.setW8(temp);
				}
			}
			
			temp = videoconfigure.getW9();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW9(null);
				}else{
					videoconfigure_id.setW9(temp);
				}
			}
			
			temp = videoconfigure.getW10();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW10(null);
				}else{
					videoconfigure_id.setW10(temp);
				}
			}
			
			temp = videoconfigure.getW11();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW11(null);
				}else{
					videoconfigure_id.setW11(temp);
				}
			}
			
			temp = videoconfigure.getW12();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW12(null);
				}else{
					videoconfigure_id.setW12(temp);
				}
			}
			
			temp = videoconfigure.getW13();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW13(null);
				}else{
					videoconfigure_id.setW13(temp);
				}
			}
			
			temp = videoconfigure.getW14();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW14(null);
				}else{
					videoconfigure_id.setW14(temp);
				}
			}
			
			temp = videoconfigure.getW15();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW15(null);
				}else{
					videoconfigure_id.setW15(temp);
				}
			}
			
			temp = videoconfigure.getW16();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW16(null);
				}else{
					videoconfigure_id.setW16(temp);
				}
			}
			
			temp = videoconfigure.getW17();
			if(temp!=null){
				if(temp.equals("empty")){
					videoconfigure_id.setW17(null);
				}else{
					videoconfigure_id.setW17(temp);
				}
			}
			configureDaoImpl.updateVideoConfigure(videoconfigure_id);
		}
		
		message.setReturn_code(MESSAGE.RETURN_SUCCESS);
		message.setReturn_msg(MESSAGE.CONFIGURE__SUCCESS);
		return message;
	}

	@Override
	public DisPatchMessage getCurrentState(String line, String station) {
		RmLine rmLine = rmLineDaoImpl.getRmline(line);
		VideoRmLine videormLine = rmLineDaoImpl.getVideoRmline(line);		
		DisPatchMessage disPatchMessage = new DisPatchMessage();
		List<Set> ls = productDaoImpl.getSet();
		if(ls.isEmpty()){
			disPatchMessage.setPdfInterval(pdfInterval);
			disPatchMessage.setVideoInterval(videoInterval);
		}else{
			disPatchMessage.setPdfInterval(ls.get(0).getPdfinterval());
			disPatchMessage.setVideoInterval(ls.get(0).getVideointerval());
		}

		if(rmLine==null || videormLine == null){
			return disPatchMessage;
		}
		if(station.equals("1")){
			CLog.Log("1 "+rmLine.getW1()+" "+videormLine.getW1());
			disPatchMessage.setPdf(rmLine.getW1());
			disPatchMessage.setVideo(videormLine.getW1());
		}else if(station.equals("2")){
			disPatchMessage.setPdf(rmLine.getW2());
			disPatchMessage.setVideo(videormLine.getW2());
		}else if(station.equals("3")){
			disPatchMessage.setPdf(rmLine.getW3());
			disPatchMessage.setVideo(videormLine.getW3());
		}else if(station.equals("4")){
			disPatchMessage.setPdf(rmLine.getW4());
			disPatchMessage.setVideo(videormLine.getW4());
		}else if(station.equals("5")){
			disPatchMessage.setPdf(rmLine.getW5());
			disPatchMessage.setVideo(videormLine.getW5());
		}else if(station.equals("6")){
			disPatchMessage.setPdf(rmLine.getW6());
			disPatchMessage.setVideo(videormLine.getW6());
		}else if(station.equals("7")){
			disPatchMessage.setPdf(rmLine.getW7());
			disPatchMessage.setVideo(videormLine.getW7());
		}else if(station.equals("8")){
			disPatchMessage.setPdf(rmLine.getW8());
			disPatchMessage.setVideo(videormLine.getW8());
		}else if(station.equals("9")){
			disPatchMessage.setPdf(rmLine.getW9());
			disPatchMessage.setVideo(videormLine.getW9());
		}else if(station.equals("10")){
			disPatchMessage.setPdf(rmLine.getW10());
			disPatchMessage.setVideo(videormLine.getW10());
		}else if(station.equals("11")){
			disPatchMessage.setPdf(rmLine.getW11());
			disPatchMessage.setVideo(videormLine.getW11());
		}else if(station.equals("12")){
			disPatchMessage.setPdf(rmLine.getW12());
			disPatchMessage.setVideo(videormLine.getW12());
		}else if(station.equals("13")){
			disPatchMessage.setPdf(rmLine.getW13());
			disPatchMessage.setVideo(videormLine.getW13());
		}else if(station.equals("14")){
			disPatchMessage.setPdf(rmLine.getW14());
			disPatchMessage.setVideo(videormLine.getW14());
		}else if(station.equals("15")){
			disPatchMessage.setPdf(rmLine.getW15());
			disPatchMessage.setVideo(videormLine.getW15());
		}else if(station.equals("16")){
			disPatchMessage.setPdf(rmLine.getW16());
			disPatchMessage.setVideo(videormLine.getW16());
		}else if(station.equals("17")){
			disPatchMessage.setPdf(rmLine.getW17());
			disPatchMessage.setVideo(videormLine.getW17());
		}
		return disPatchMessage;
	}





}
