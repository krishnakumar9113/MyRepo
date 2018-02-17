package com.mycafeteria.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MultivaluedMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.mycafeteria.bean.Order;
import com.mycafeteria.bean.User;
import com.mycafeteria.persistance.HibernateUtil;

public class UserFactory {
	private List<User> users = new ArrayList<User>();
	SessionFactory sessionFactory;
	Session session;
	Transaction tx = null;

	public UserFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();

	}

	public List<User> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			users = session.createQuery("from User").list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.flush();
			session.close();
			// sessionFactory.close();
		}
		return users;
	}

	public User getModelById(int id) {
		// TODO Auto-generated method stub

		User user = null;
		session = sessionFactory.openSession();
		try {
			tx = session.beginTransaction();
			user = (User) session.get(User.class, id);

		} catch (HibernateException e) {
			throw e;
		} finally {
			session.flush();
			session.close();
			// sessionFactory.close();
		}

		return user;
	}

	public void delete(int id)
			throws org.hibernate.exception.ConstraintViolationException,
			Exception {
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object userObject = session.load(User.class, id);
			User user = (User) userObject;
			session.delete(user);
			tx.commit();
		}

		catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.flush();
			session.close();
			// sessionFactory.close();
		}

	}

	public void put(User user)
			throws org.hibernate.exception.ConstraintViolationException,
			Exception {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(user);
			tx.commit();
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

	}

	public List<User> authenticate(String username) throws Exception {
		List<User> result;

		try {

			session = sessionFactory.openSession();

			String hql = " from  User where UserName='" + username + "'";

			Query query = session.createQuery(hql);

			result = query.list();

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

	public List<User> checkUserWithMailID(String mailid) throws Exception {
		List<User> result;
		try {

			session = sessionFactory.openSession();

			String hql = " from  User where mailid='" + mailid + "'";

			Query query = session.createQuery(hql);

			result = query.list();

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

	public List<User> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(User.class);
			Criterion[] c = new Criterion[filterBy.size()];
			int i = 0, j;
			for (String s : keys) {
				String value = filterBy.get(s).toString().replace("[", "")
						.replace("]", "");
				Pattern p = Pattern.compile("^[0-9]+$");
				Matcher m = p.matcher(value);
				if (m.find()) {
					cr = cr.add(Restrictions.eq(s, Integer.parseInt(value)));
				} else
					cr = cr.add(Restrictions.eq(s, value));
				i++;
			}
			users = cr.list();

		} catch (HibernateException e) {
			throw e;
		} catch (ClassCastException e1) {
			throw e1;
		} finally {
			session.flush();
			session.close();
			// sessionFactory.close();
		}
		return users;
	}

	public void post(User user)
			throws org.hibernate.exception.ConstraintViolationException,
			Exception {
		try {
			session = sessionFactory.openSession();

			tx = session.beginTransaction();
			session.save(user);
			tx.commit();

		} catch (ConstraintViolationException ex) {
			throw ex;
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
	}

}
