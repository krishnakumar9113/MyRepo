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

import com.mycafeteria.bean.Location;
import com.mycafeteria.bean.User;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.persistance.HibernateUtil;

	

public class VendorFactory {
	private List<Vendor> vendors = new ArrayList<Vendor>();
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	
	public VendorFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
	
	}

	public List<Vendor> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			vendors = session.createQuery("from Vendor").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return vendors;
	}

	

	public Vendor getModelById(int id) {
		// TODO Auto-generated method stub
		
		Vendor vendor = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			vendor = (Vendor) session.get(Vendor.class, id);	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return vendor;
	}
	public List<Vendor> getModelByLocationId(int locationid) {
		// TODO Auto-generated method stub
		
		List<Vendor> vendor = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			vendor =  session.createQuery("from Vendor where LocationID="+locationid).list();	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return vendor;
	}
	public List<Vendor> getModelByMailID(String mailid) {
		// TODO Auto-generated method stub
		
		List<Vendor> vendor = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			vendor =  session.createQuery("from Vendor where mail='"+mailid+"'").list();	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return vendor;
	}

	
	
	public void deleteVendor(int id) throws org.hibernate.exception.ConstraintViolationException,Exception {
		try{	
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object vendorObject = session.load(Vendor.class, id);
			Vendor vendor = (Vendor) vendorObject;		
			session.delete(vendor);
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

	public void putVendor(Vendor vendor) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(vendor);
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

	public List<Vendor> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(Vendor.class).add(Restrictions.eq("IsActive", new Boolean(true)));
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
			vendors = cr.list();
		
			
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
		return vendors;
	}

	public void put(Vendor vendor) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		
		
		try {
			session = sessionFactory.openSession();
			
				tx = session.beginTransaction();
				session.merge(vendor);
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
	
	public void updateVendorPassword(Integer id,String password) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		
		
		try {
			session = sessionFactory.openSession();
			String hql = "UPDATE Vendor set password = :pass "  + 
		             "WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("pass", password);
		query.setParameter("id", id);
		int result = query.executeUpdate();
	
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
	
	public void post(Vendor vendor) throws org.hibernate.exception.ConstraintViolationException,Exception{
		try
		{
			session = sessionFactory.openSession();
	
				tx = session.beginTransaction();
				session.save(vendor);
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
	public Vendor authenticate(String mailid) throws Exception{
		Vendor result = null;
		try {
	
			session = sessionFactory.openSession();
			
			String hql =" from  Vendor where Mail='"+mailid+"'"; 
		
		Query query = session.createQuery(hql);
		
		
		if(query.list().size()>0){
			result=(Vendor)	query.list().get(0);
		}
	
			
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
		return result;
		
	}
	
	public Vendor forgotPassword(String mailid) throws Exception{
		Vendor result;
		try {
	
			session = sessionFactory.openSession();
			
			String hql =" from  Vendor where mailid='"+mailid+"'"; 
		
		Query query = session.createQuery(hql);
		
		result = (Vendor) query.list().get(0);
	
			
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
		return result;
		
	}
	
}
