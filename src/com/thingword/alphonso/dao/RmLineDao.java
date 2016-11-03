package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.RmLine;

public interface RmLineDao {
	public String getPath(String line,String station);
	public boolean updateRmLine(RmLine rmLine);
	public RmLine getRmline(String line);
}
