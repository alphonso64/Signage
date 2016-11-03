package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.db.Configure;

public interface ConfigureDao {
	public boolean updateConfigure(Configure configure);
	public boolean deleteConfigure(Configure configure);
	public Configure getConfigure(String invcode);
}
