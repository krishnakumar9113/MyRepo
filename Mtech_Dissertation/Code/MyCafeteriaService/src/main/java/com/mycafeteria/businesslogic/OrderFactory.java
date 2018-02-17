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

import com.mycafeteria.bean.Category;
import com.mycafeteria.bean.Location;
import com.mycafeteria.bean.Order;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.persistance.HibernateUtil;

	

public class OrderFactory {
	private List<Order> orders = new ArrayList<Order>();
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	
	public OrderFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
	
	}

	public List<Order> getModel() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			orders = session.createQuery("from Order").list();			 
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
			//sessionFactory.close();
		}
		return orders;
	}

	public List<Order> getModelBySecretCode(String Code) {
		// TODO Auto-generated method stub

		
		List<Order> order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order =   session.createQuery("from Order where secretCode='"+Code+"'").list();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}
	public List<Order> getModelByVendor(int vendorid) {
		// TODO Auto-generated method stub

		
		List<Order> order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order = session.createQuery("from Order where VendorID="+vendorid+" AND Status IN ('paid','printed')").list();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}
	public List<Order> getModelByUserid(int userid) {
		// TODO Auto-generated method stub

		
		List<Order> order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order = session.createQuery("from Order where Userid="+userid+" AND Status IN ('paid','printed')").list();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}
	

	public List<Order> getModelByVendorpaid(int vendorid,int itemid) {
		// TODO Auto-generated method stub

		
		List<Order> order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order =  session.createQuery("from Order where VendorID="+vendorid+" AND Status IN ('paid','printed') AND Items_count like '%"+itemid+":%' ").list();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}
	
	public int deleteItemsByVendorExpired(int vendorid,int itemid) {
		// TODO Auto-generated method stub

		int result;
		Query order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order =  session.createQuery("DELETE from Order where VendorID="+vendorid+" AND Status IN ('expired') AND Items_count like '%"+itemid+":%' ");
			result=order.executeUpdate();
			tx.commit();
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return result;
	}
	
	public List<Order> getModelByitemspaid(int itemid) {
		// TODO Auto-generated method stub

		
		List<Order> order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order =  session.createQuery("from Order where Status IN ('paid','printed') AND Items_count like '%"+itemid+":%' ").list();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}
	
	public int deleteModelByItemsExpired(int itemid) {
		// TODO Auto-generated method stub
		int result;
		Query order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order =  session.createQuery("DELETE from Order where Status IN ('expired') AND Items_count like '%"+itemid+":%' ");
			result=order.executeUpdate();
			tx.commit();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return result;
	}
	

	public Order getModelById(int id) {
		// TODO Auto-generated method stub
		
		Order order = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			order = (Order) session.get(Order.class, id);	
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return order;
	}

	public void delete(int id) throws org.hibernate.exception.ConstraintViolationException,Exception {
		try{	
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object orderObject = session.load(Order.class, id);
			Order order = (Order) orderObject;		
			session.delete(order);
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

	public void put(Order order) throws org.hibernate.exception.ConstraintViolationException,Exception{
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.merge(order);
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

	public List<Order> getModelWithFilter(MultivaluedMap<String, String> filterBy) {
		// TODO Auto-generated method stub
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Set<String> keys = filterBy.keySet();
			Criteria cr = session.createCriteria(Order.class);
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
			orders = cr.list();
		
			
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
		return orders;
	}

	
	public void post(Order order) throws org.hibernate.exception.ConstraintViolationException,Exception{
		try
		{
			session = sessionFactory.openSession();
	
				tx = session.beginTransaction();
				session.save(order);
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
	
	public int markOrdersAsExpired(int vendorid) {
		// TODO Auto-generated method stub
		int result;
		Query loc = null;
		session = sessionFactory.openSession();
		try
		{
			tx = session.beginTransaction();
			loc =  session.createQuery("UPDATE Order SET Status='expired' where VendorID="+vendorid);
			result=loc.executeUpdate();
			tx.commit();
			
		}
		catch (HibernateException e) {
			throw e;
		}		
		finally{
			session.flush();
			session.close();
		//	sessionFactory.close();
		}
	
		return result;
	}
	
	
}
