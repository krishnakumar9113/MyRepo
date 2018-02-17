package com.mycafeteria.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MultivaluedMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.mycafeteria.bean.Category;
import com.mycafeteria.persistance.HibernateUtil;

	

public class CategoryFactory {
	private List<Category> Categories = new ArrayList<Category>();
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	
	public CategoryFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
	
	}

	public List<Category> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			Categories = session.createQuery("from Category").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return Categories;
	}

	

	public Category getModelById(int id) {
		// TODO Auto-generated method stub
		
		Category loc = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			loc = (Category) session.get(Category.class, id);	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return loc;
	}

	public void delete(int id) throws org.hibernate.exception.ConstraintViolationException,Exception {
		try{	
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object object1 = session.load(Category.class, id);
			Category testObject = (Category) object1;		
			session.delete(testObject);
			tx.commit();
		}
		
		catch (Exception e) {
			// TODO: handle exception
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
		
	}

	public void put(Category category) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(category);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
		
	}

	public List<Category> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(Category.class);
			Criterion[] c = new Criterion[filterBy.size()];
			int i=0,j;
			for(String s :keys)
			{
				String value = filterBy.get(s).toString().replace("[", "").replace("]", "");
				Pattern p = Pattern.compile("^[0-9]+$");
				Matcher m = p.matcher(value);
				if (m.find()) {				    
				    cr = cr.add(Restrictions.eq(s,Integer.parseInt(value)));
				}
				else
					 cr = cr.add(Restrictions.eq(s,value));
				i++;
			}
			Categories = cr.list();
		
			
		}
		catch (HibernateException e) {
			throw e; 
		}	
		 catch (ClassCastException e1){
			 throw e1;
		 }
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
		return Categories;
	}

	
	public void post(Category category) throws org.hibernate.exception.ConstraintViolationException,Exception{
		try
		{
			session = sessionFactory.openSession();
	
				tx = session.beginTransaction();
				session.save(category);
				tx.commit();
			
		}catch(ConstraintViolationException ex){
			throw ex;
		}
		catch (Exception e) {
			// TODO: handle exception
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	}
	
}
