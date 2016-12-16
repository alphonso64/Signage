package com.thingword.alphonso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.loader.custom.Return;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.hibernate.secure.internal.DisabledJaccServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.bean.DisPatchMessage;
import com.thingword.alphonso.bean.DispatchFile;
import com.thingword.alphonso.bean.ReturnConfigureData;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.SetConfigure;
import com.thingword.alphonso.bean.WebSocketID;
import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.Set;
import com.thingword.alphonso.service.UploadService;
import com.thingword.alphonso.service.impl.DispatchServiceImpl;
import com.thingword.alphonso.service.impl.ProductServiceImpl;
import com.thingword.alphonso.service.impl.UploadServiceImpl;
import com.thingword.alphonso.util.CLog;
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
	
//	//forSql
//	@GET
//	@Path("/forSql")
//	public ReturnMessage forSql() {
//		CLog.Log("forSql");
//		productServiceImpl.forSql();
//		return null;
//	}

	// 获取电子工艺指导书列表
	@GET
	@Path("/getProductList")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<Product> getProductList() {
		return productServiceImpl.ListAllProduct();
	}
	
	// 获取电子工艺指导书列表
	@GET
	@Path("/getSet")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<Set> getSet() {
		return productServiceImpl.getSet();
	}
	
	// 获取电子工艺指导书列表
	@GET
	@Path("/getRmLineList")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<String> getRmLineList() {
		return productServiceImpl.ListRmLine();
	}

	@GET
	@Path("/getUniversalVideoList")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnData<DispatchFile> getUniversalVideoList() {
		return uploadServiceImpl.getUniversalVideoList();
	}

	// 换线下发
	@GET
	@Path("/dispatchFiles")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage dispatchFiles(@QueryParam("productionline") String productline,
			@QueryParam("invcode") String invcode) {
		return dispatchServiceImpl.dispatchFileTosWorker(productline.toUpperCase(), invcode.toUpperCase());

	}

	@GET
	@Path("/reqDispatchFileDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnConfigureData<DispatchFile> reqDispatchFileDetails(@QueryParam("invcode") String invcode) {
		return dispatchServiceImpl.getDispatchFileDetail(invcode.toUpperCase());
	}

	@POST
	@Path("/uploadProductCraftResource")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage uploadProductCraftResource(@FormDataParam("filepath") InputStream uploadedInputStream,
			@FormDataParam("filepath") FormDataContentDisposition fileDetail) {
		return uploadServiceImpl.uploadProductCraftResource(fileDetail.getFileName(), uploadedInputStream);

	}

	@POST
	@Path("/uploadUniversalVideoResource")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage uploadUniversalVideoResource(@FormDataParam("filepath") InputStream uploadedInputStream,
			@FormDataParam("filepath") FormDataContentDisposition fileDetail) {
		return uploadServiceImpl.uploadUniversalVideoesource(fileDetail.getFileName(), uploadedInputStream);

	}
	
	@POST
	@Path("/updateSet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage updateSet(Set set) {
		return productServiceImpl.updateSet(set);

	}

	@GET
	@Path("/reqUpdatePDF")
	@Produces("application/pdf")
	public byte[] reqDispatchPdf(@QueryParam("productionline") String productline,
			@QueryParam("station") String station, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		byte[] bytes = dispatchServiceImpl.getDispatchFile(productline.toUpperCase(), station);
		response.addHeader("Content-Length", String.valueOf(bytes.length));
		return bytes;
	}

	@GET
	@Path("/reqCurrentState")
	@Produces(MediaType.APPLICATION_JSON)
	public DisPatchMessage reqCurrentState(@QueryParam("productionline") String productline,
			@QueryParam("station") String station, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		DisPatchMessage disPatchMessage = dispatchServiceImpl.getCurrentState(productline, station);
		return disPatchMessage;
	}

	// @GET
	// @Path("/reqUpdateVideo")
	// @Produces("video/webm")
	// public byte[] reqDispatchVideo(@QueryParam("productionline") String
	// productline,
	// @QueryParam("station") String station, @Context HttpServletRequest
	// request,
	// @Context HttpServletResponse response) {
	// byte[] bytes =
	// dispatchServiceImpl.getVideoDispatchFile(productline.toUpperCase(),
	// station);
	// response.addHeader("Content-Length", String.valueOf(bytes.length));
	// response.addHeader("Connection", "keep-alive");
	// return bytes;
	// }

	@GET
	@Path("/reqUpdateVideo")
	@Produces("video/webm")
	public Response streamExample(@QueryParam("productionline") String productline,
			@QueryParam("station") String station) {
		String filePath = dispatchServiceImpl.getVideoDispatchFilePath(productline, station);
		File file = new File(filePath);
		StreamingOutput stream = null;
		int size = 0;
		try {
			final InputStream in = new FileInputStream(file);
			size = in.available();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(stream).header("Connection", "keep-alive").header("Content-Length", String.valueOf(size))
				.build();
	}
	
//	@GET
//	@Path("/reqUpdateVideo")
//	@Produces("video/webm")
//	public Response streamExample(@QueryParam("productionline") String productline,
//			@QueryParam("station") String station,@HeaderParam("Range") String range) throws Exception {
//		String filePath = dispatchServiceImpl.getVideoDispatchFilePath(productline, station);
//		File file = new File(filePath);
//		return productServiceImpl.buildStream(file, range);
//	}
	

	@POST
	@Path("/setConfigure")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage setConfigure(SetConfigure setConfigure) {
		// CLog.Log("setConfigure");
		return dispatchServiceImpl.setConfigure(setConfigure);
	}

	@POST
	@Path("/setSpecificConfigure")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReturnMessage setSpecificConfigure(SetConfigure configure) {
		return dispatchServiceImpl.setSpecificConfigure(configure);
	}

	@GET
	@Path("/reqpdffile")
	@Produces("application/pdf")
	public byte[] reqpdffile(@QueryParam("path") String path, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		byte[] bytes = FileUtil.getFile(path);
		response.addHeader("Content-Length", String.valueOf(bytes.length));
		return bytes;
	}
	
	@GET
	@Path("/reqvideofile")
	@Produces("video/webm")
	public Response reqvideofile(@QueryParam("path") String path, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		CLog.Log("reqvideofile"+path);
		File file = new File(path);
		StreamingOutput stream = null;
		int size = 0;
		try {
			final InputStream in = new FileInputStream(file);
			size = in.available();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(stream).header("Connection", "keep-alive").header("Content-Length", String.valueOf(size))
				.build();
	}

}