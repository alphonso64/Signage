package com.thingword.alphonso.service.impl;

import java.util.List;

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
}
