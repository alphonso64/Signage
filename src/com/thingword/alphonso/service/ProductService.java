package com.thingword.alphonso.service;

import java.util.List;

import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.ReturnMessage;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.Set;

public interface ProductService {
	public ReturnData<Product> ListAllProduct();
	public ReturnData<String> ListRmLine();
	public ReturnMessage updateSet(Set set);
	public ReturnData<Set> getSet();
}
