package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.dao.ProductDao;
import com.thingword.alphonso.util.HibernateUtil;

public class ProductDaoImpl implements ProductDao{
	public List<Product> ListAllProduct() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		List<Product> ls = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "From Product ";
			Query query = s.createQuery(hql);
			ls = query.list();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return ls;
	}

	@Override
	public boolean updateProduct(Product product) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteProuct(product);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			s.save(product);
			t.commit();
			flag = true;
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return flag;
	}

	@Override
	public boolean deleteProuct(Product product) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from Product where invcode = '" + product.getInvcode() + "'";
			s.createQuery(hql).executeUpdate();
			t.commit();
			flag = true;
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return flag;
	}

	@Override
	public Product getProduct(String invcode) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		Product product = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "from Product where invcode=" + "'" + invcode + "'";
			Query query = s.createQuery(hql);
			product = (Product) query.uniqueResult();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return product;
	}
}
