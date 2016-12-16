package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.bean.db.VideoRmLine;

public interface RmLineDao {
	public String getPath(String line,String station);
	public boolean updateRmLine(RmLine rmLine);
	public RmLine getRmline(String line);
	
	public String getVideoath(String line,String station);
	public boolean updateVideoRmLine(VideoRmLine rmLine);
	public VideoRmLine getVideoRmline(String line);
	
	public List<String> ListRmLine();
	
	public void deleteNULLRmLine();
}
