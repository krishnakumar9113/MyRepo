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

import com.mycafeteria.bean.Location;
import com.mycafeteria.persistance.HibernateUtil;

	

public class LocationFactory {
	private List<Location> locations = new ArrayList<Location>();
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	
	public LocationFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
	
	}

	public List<Location> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			locations = session.createQuery("from Location").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return locations;
	}

	public List<Location> getModelbyCode(String code) {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			locations = session.createQuery("from Location where Code='"+code+"'").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return locations;
	}


	public Location getModelById(int id) {
		// TODO Auto-generated method stub
		
		Location location = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			location = (Location) session.get(Location.class, id);	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return location;
	}

	public void deleteLocation(int id) throws org.hibernate.exception.ConstraintViolationException,Exception {
		try{	
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object locationobj = session.load(Location.class, id);
			Location location = (Location) locationobj;		
			session.delete(location);
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

	public void putLocation(Location location) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(location);
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

	public List<Location> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(Location.class);
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
			locations = cr.list();
		
			
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
		return locations;
	}

	public void putLocationModel(Location location) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		
		
		try {
			session = sessionFactory.openSession();
			
				tx = session.beginTransaction();
				session.merge(location);
				tx.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			
			session.flush();
			session.close();
			//sessionFactory.close();
		}
	}
	public void postLocation(Location location) throws org.hibernate.exception.ConstraintViolationException,Exception{
		try
		{
			session = sessionFactory.openSession();
	
				tx = session.beginTransaction();
				session.save(location);
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
