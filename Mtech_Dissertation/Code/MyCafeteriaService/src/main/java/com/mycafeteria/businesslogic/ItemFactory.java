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

import com.mycafeteria.bean.Item;
import com.mycafeteria.persistance.HibernateUtil;

	

public class ItemFactory {
	private List<Item> items = new ArrayList<Item>();
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	
	public ItemFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
	
	}

	public List<Item> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			items = session.createQuery("from Item").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return items;
	}

	
	public List<Item> getModelByVendorId(int id) {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			items = session.createQuery("from Item Where VendorID="+id).list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return items;
	}
/*	
	public List<Item> getModelbyvenidcatid(int venid,int catid) {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			Apps = session.createQuery("from Item Where CategoryID="+catid+" AND VendorID="+venid).list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return Apps;
	}
*/
	public List<Item> getModelByCategoryId(int id) {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			items = session.createQuery("from Item Where CategoryID="+id).list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return items;
	}
	public Item getModelById(int id) {
		// TODO Auto-generated method stub
		
		Item item = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			item = (Item) session.get(Item.class, id);	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return item;
	}

	public void delete(int id) throws org.hibernate.exception.ConstraintViolationException,Exception {
		try{	
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object itemObj = session.load(Item.class, id);
			Item item = (Item) itemObj;		
			session.delete(item);
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

	public void put(Item item) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(item);
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

	public List<Item> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(Item.class).add(Restrictions.gt("count", new Integer(0)));
			cr.add(Restrictions.eq("IsActive", new Boolean(true)));
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
			items = cr.list();
		
			
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
		return items;
	}

	
	public void post(Item item) throws org.hibernate.exception.ConstraintViolationException,Exception{
		try
		{
			session = sessionFactory.openSession();
	
				tx = session.beginTransaction();
				session.save(item);
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
