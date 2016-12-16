package com.thingword.alphonso.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.eclipse.jdt.internal.compiler.lookup.InvocationSite.EmptyWithAstNode;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.bean.MESSAGE;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.Set;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.dao.impl.RmLineDaoImpl;
import com.thingword.alphonso.service.ProductService;
import com.thingword.alphonso.util.MediaStreamer;

public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDaoImpl productDaoImpl;
    @Autowired
    private RmLineDaoImpl rmLineDaoImpl;
	public ReturnData<Product>ListAllProduct() {
		ReturnData<Product> data = new ReturnData<>();
		data.setReturn_code(MESSAGE.RETURN_FAIL);
		
		List<Product> ls= productDaoImpl.ListAllProduct();
		data.setReturn_code(MESSAGE.RETURN_SUCCESS);
		data.setData(ls);
		return data;
	}
	@Override
	public ReturnData<String> ListRmLine() {
		ReturnData<String> data = new ReturnData<>();
		data.setReturn_code(MESSAGE.RETURN_FAIL);
		
		List<String> ls= rmLineDaoImpl.ListRmLine();
		data.setReturn_code(MESSAGE.RETURN_SUCCESS);
		data.setData(ls);
		return data;
	}
	@Override
	public ReturnMessage updateSet(Set set) {
		ReturnMessage returnMessage = new ReturnMessage();
		returnMessage.setReturn_msg(MESSAGE.RETURN_SUCCESS);
		returnMessage.setReturn_msg(MESSAGE.SET__SUCCESS);
		productDaoImpl.updateSet(set);
		return returnMessage;
	}
	@Override
	public ReturnData<Set> getSet() {
		ReturnData<Set> data = new ReturnData<>();
		
		List<Set> ls= productDaoImpl.getSet();
		data.setReturn_code(MESSAGE.RETURN_SUCCESS);
		data.setData(ls);
		return data;
	}
	@Override
	public void forSql() {
		rmLineDaoImpl.deleteNULLRmLine();	
	}
	
	public Response buildStream(final File asset, final String range) throws Exception {
	        // range not requested : Firefox, Opera, IE do not send range headers
	        if (range == null) {
	            StreamingOutput streamer = new StreamingOutput() {
	                @Override
	                public void write(final OutputStream output) throws IOException, WebApplicationException {

	                    final FileChannel inputChannel = new FileInputStream(asset).getChannel();
	                    final WritableByteChannel outputChannel = Channels.newChannel(output);
	                    try {
	                        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
	                    } finally {
	                        // closing the channels
	                        inputChannel.close();
	                        outputChannel.close();
	                    }
	                }
	            };
	            return Response.ok(streamer).status(200).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
	        }

	        String[] ranges = range.split("=")[1].split("-");
	        final int from = Integer.parseInt(ranges[0]);
	        /**
	         * Chunk media if the range upper bound is unspecified. Chrome sends "bytes=0-"
	         */
	        int chunk_size = 1024 * 1024; 
	        int to = chunk_size + from;
	        if (to >= asset.length()) {
	            to = (int) (asset.length() - 1);
	        }
	        if (ranges.length == 2) {
	            to = Integer.parseInt(ranges[1]);
	        }

	        final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
	        final RandomAccessFile raf = new RandomAccessFile(asset, "r");
	        raf.seek(from);

	        
	        final int len = to - from + 1;
	        final MediaStreamer streamer = new MediaStreamer(len, raf);
	        Response.ResponseBuilder res = Response.ok(streamer).status(206)
	                .header("Accept-Ranges", "bytes")
	                .header("Content-Range", responseRange)
	                .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
	                .header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
	        return res.build();
	    }
}
