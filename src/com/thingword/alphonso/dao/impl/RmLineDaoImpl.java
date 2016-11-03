package com.thingword.alphonso.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.thingword.alphonso.bean.db.RmLine;
import com.thingword.alphonso.dao.RmLineDao;
import com.thingword.alphonso.util.HibernateUtil;

public class RmLineDaoImpl implements RmLineDao {
	public String getPath(String line, String station) {
		String val = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		RmLine rmLine = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "from RmLine where line=" + "'" + line + "'";
			Query query = s.createQuery(hql);
			rmLine = (RmLine) query.uniqueResult();
			t.commit();
			if (station.equals("1")) {
				val = rmLine.getW1();
			} else if (station.equals("2")) {
				val = rmLine.getW2();
			} else if (station.equals("3")) {
				val = rmLine.getW3();
			} else if (station.equals("4")) {
				val = rmLine.getW4();
			} else if (station.equals("5")) {
				val = rmLine.getW5();
			} else if (station.equals("6")) {
				val = rmLine.getW6();
			} else if (station.equals("7")) {
				val = rmLine.getW7();
			} else if (station.equals("8")) {
				val = rmLine.getW8();
			} else if (station.equals("9")) {
				val = rmLine.getW9();
			} else if (station.equals("10")) {
				val = rmLine.getW10();
			} else if (station.equals("11")) {
				val = rmLine.getW11();
			} else if (station.equals("12")) {
				val = rmLine.getW12();
			} else if (station.equals("13")) {
				val = rmLine.getW13();
			} else if (station.equals("14")) {
				val = rmLine.getW14();
			} else if (station.equals("15")) {
				val = rmLine.getW15();
			} else if (station.equals("16")) {
				val = rmLine.getW16();
			} else if (station.equals("17")) {
				val = rmLine.getW17();
			}
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}

		return val;
	}

	@Override
	public boolean updateRmLine(RmLine rmLine) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		boolean flag = false;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			s.update(rmLine);
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
	public RmLine getRmline(String line) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session s = null;
		Transaction t = null;
		RmLine rmLine = null;
		try {
			s = sessionFactory.openSession();
			t = s.beginTransaction();
			String hql = "from RmLine where line=" + "'" + line + "'";
			Query query = s.createQuery(hql);
			rmLine = (RmLine) query.uniqueResult();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
		return rmLine;
	}
}
