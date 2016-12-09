package com.thingword.alphonso.dao;

import java.util.List;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.Set;

public interface ProductDao {
	public List<Product> ListAllProduct();
	public boolean updateProduct(Product product);
	public boolean deleteProuct(Product product);
	public Product getProduct(String invcode);
	
	public boolean updateSet(Set set);
	public List<Set> getSet();
}
