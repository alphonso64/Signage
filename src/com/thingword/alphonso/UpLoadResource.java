package com.thingword.alphonso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.hibernate.secure.internal.DisabledJaccServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.WebSocketID;
import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.service.UploadService;
import com.thingword.alphonso.service.impl.DispatchServiceImpl;
import com.thingword.alphonso.service.impl.ProductServiceImpl;
import com.thingword.alphonso.service.impl.UploadServiceImpl;
import com.thingword.alphonso.util.FileUtil;

import javassist.ByteArrayClassPath;

@Path("/upload")
public class UpLoadResource {

	@Autowired
	private ProductServiceImpl productServiceImpl;
	@Autowired
	private UploadServiceImpl uploadServiceImpl;
	@Autowired
	private DispatchServiceImpl dispatchServiceImpl;

	@POST
	@Path("/getPro")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getPro() {
		return productServiceImpl.ListAllProduct();
	}

	@GET
	@Path("/dispatchFiles")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage dispatchFiles(@QueryParam("productionline") String productline,
			@QueryParam("invcode") String invcode) {
//		System.out.println("dispatchFiles" + productline + " " + invcode);
		return dispatchServiceImpl.dispatchFileTosWorker(productline, invcode);

	}

	@GET
	@Path("/reqDispatchFileDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<DispatchFile> reqDispatchFileDetails(@QueryParam("invcode") String invcode) {
		return dispatchServiceImpl.getDispatchFileDetail(invcode);
	}

	@POST
	@Path("/uploadProductCraftResource")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage uploadProductCraftResource(@FormDataParam("filepath") InputStream uploadedInputStream,
			@FormDataParam("filepath") FormDataContentDisposition fileDetail) {
		return uploadServiceImpl.uploadProductCraftResource(fileDetail.getFileName(), uploadedInputStream);

	}

	@GET
	@Path("/self")
	@Produces("application/pdf")
	public byte[] reqDispatchPdf(@QueryParam("productionline") String productline,
			@QueryParam("station") String station, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
//		System.out.println("self:" + productline + " " + station);
		byte[] bytes = dispatchServiceImpl.getDispatchFile(productline, station);
		response.addHeader("Content-Length", String.valueOf(bytes.length));
		return bytes;
	}

	@POST
	@Path("/setConfigure")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage setConfigure(Configure configure) {
		return dispatchServiceImpl.setConfigure(configure);
	}

	@GET
	@Path("/getconfigure")
	@Produces(MediaType.APPLICATION_JSON)
	public Configure getconfigure() {
		Configure configure = new Configure();
		return configure;
	}

	@GET
	@Path("/reqpdffile")
	@Produces("application/pdf")
	public byte[] reqpdffile(@QueryParam("path") String path, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
//		System.out.println("reqpdffile:" + path);
		byte[] bytes = FileUtil.getFile(path);
		response.addHeader("Content-Length", String.valueOf(bytes.length));
		return bytes;
	}

}