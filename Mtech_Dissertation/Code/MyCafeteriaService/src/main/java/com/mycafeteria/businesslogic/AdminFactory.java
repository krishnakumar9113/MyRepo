package com.mycafeteria.businesslogic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mycafeteria.bean.Admin;
import com.mycafeteria.persistance.HibernateUtil;

public class AdminFactory {
	private List<Admin> Adminlist = new ArrayList<Admin>();
	SessionFactory sessionFactory;
	Session session;
	Transaction tx = null;

	public AdminFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();

	}

	public Admin getModel() {
		// TODO Auto-generated method stub
		Admin result = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			Adminlist = session.createQuery("from Admin").list();

			if (Adminlist.size() > 0) {
				result = (Admin) Adminlist.get(0);
			}
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.flush();
			session.close();
			// sessionFactory.close();
		}
		return result;
	}

	public Admin authenticate(String mail) throws Exception {
		Admin result = null;
		try {

			session = sessionFactory.openSession();

			String hql = " from  Admin where MailId='" + mail + "'";

			Query query = session.createQuery(hql);

			if (query.list().size() > 0) {
				result = (Admin) query.list().get(0);
			}

		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {

			session.flush();
			session.close();
			// sessionFactory.close();
		}
		return result;

	}

}
