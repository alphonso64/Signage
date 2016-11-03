package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.db.Configure;
import com.thingword.alphonso.bean.db.Product;
import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.dao.ConfigureDao;
import com.thingword.alphonso.dao.ProductDao;
import com.thingword.alphonso.util.HibernateUtil;

public class ConfigureDaoImpl implements ConfigureDao{

	@Override
	public boolean updateConfigure(Configure configure) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		deleteConfigure(configure);
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			s.save(configure);
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
	public boolean deleteConfigure(Configure configure) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "delete from Configure where invcode = '" + configure.getInvcode() + "'";
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
	public Configure getConfigure(String invcode) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		Configure configure = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "from Configure where invcode=" + "'" + invcode + "'";
			Query query = s.createQuery(hql);
			configure = (Configure) query.uniqueResult();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return configure;
	}
}
