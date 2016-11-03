package com.thingword.alphonso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.dao.impl.ProductDaoImpl;
import com.thingword.alphonso.service.ProductService;

public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDaoImpl productDaoImpl;
	public List<Product> ListAllProduct() {
		return productDaoImpl.ListAllProduct();
	}
}
