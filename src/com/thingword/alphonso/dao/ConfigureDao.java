package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.VideoConfigure;

public interface ConfigureDao {
	public boolean updateConfigure(Configure configure);
	public boolean deleteConfigure(Configure configure);
	public Configure getConfigure(String invcode);
	
	
	public boolean updateVideoConfigure(VideoConfigure configure);
	public boolean deleteVideoConfigure(VideoConfigure configure);
	public VideoConfigure getVideoConfigure(String invcode);	
}
