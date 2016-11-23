package com.thingword.alphonso.service;

import java.util.List;

import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.db.Product;

public interface ProductService {
	public ReturnData<Product> ListAllProduct();
}
