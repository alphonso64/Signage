package com.thingword.alphonso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.bean.MESSAGE;
import com.thingword.alphonso.bean.ReturnData;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.service.ProductService;

public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDaoImpl productDaoImpl;
	public ReturnData<Product>ListAllProduct() {
		ReturnData<Product> data = new ReturnData<>();
		data.setReturn_code(MESSAGE.RETURN_FAIL);
		
		List<Product> ls= productDaoImpl.ListAllProduct();
		if(!ls.isEmpty()){
			data.setReturn_code(MESSAGE.RETURN_SUCCESS);
			data.setData(ls);
		}
		return data;
	}
}
