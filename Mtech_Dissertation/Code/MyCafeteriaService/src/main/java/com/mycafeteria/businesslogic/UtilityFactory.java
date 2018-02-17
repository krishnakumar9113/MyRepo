package com.mycafeteria.businesslogic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mycafeteria.bean.Category;
import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.ItemMobile;
import com.mycafeteria.bean.Location;
import com.mycafeteria.bean.Order;
import com.mycafeteria.bean.User;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.bean.VendorMobile;
import com.mycafeteria.persistance.HibernateUtil;

public class UtilityFactory {
	SessionFactory sessionFactory;
	Session session; 
	Transaction tx = null;
	CategoryFactory categoryFactory;
	LocationFactory locationFactory;
	public UtilityFactory() {
		// TODO Auto-generated constructor stub
		sessionFactory = HibernateUtil.getSessionFactory();	
		categoryFactory= new CategoryFactory();
		locationFactory= new LocationFactory();
	}
	//Selection of categories related to loc -
	public List<Category> selectCategoriesByLocationId(int locationid) throws Exception{
		List<Integer> result =new ArrayList<Integer>() ;
		List<Category> categorylist;
	
		try {
			session = sessionFactory.openSession();
			String sqlquery ="SELECT distinct CategoryID from Item where VendorID IN"+
					"(SELECT id FROM Vendor where LocationID='"+locationid+"' AND isActive=1)"; 
		
		Query query = session.createQuery(sqlquery);
		
		result = query.list();
	 categorylist= new ArrayList<Category>();
	for(Integer item:result){
		categorylist.add(categoryFactory.getModelById(item));
		
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
		return categorylist;
		
	}
	public List<VendorMobile> selectVendorsByCategoryIdAndLocationId(Integer categoryid ,Integer locationid) throws Exception{

		List<VendorMobile> vendormobilelist;
		try {
			session = sessionFactory.openSession();
			String sqlquery="Select new VendorMobile (id,Name) from Vendor where LocationID="+locationid+" AND isActive=1 AND id IN (SELECT distinct VendorID FROM Item where CategoryID='"+categoryid+"')";
			
		
		Query query = session.createQuery(sqlquery);
		
		vendormobilelist = query.list();
	/*	Venmoblist= new ArrayList<VendorMobile>();
	for(Vendor item:result){
		if(item.getLocationID().equals(Locid)){
			
			Venmoblist.add(new VendorMobile(item.getId(),item.getName()));
		}
		
		
	}*/
			
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
		return vendormobilelist;
		
	}
	
	
	public List<Category> selectCategoriesByvenidLocid(Integer vendorid ) throws Exception{
		List<Category> result =new ArrayList<Category>() ;
	
		try {
			session = sessionFactory.openSession();
			String hql="from Category where id IN(SELECT distinct CategoryID FROM Item where VendorID='"+vendorid+"')";
		
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
			//sessionFactory.close();
		}
		return result;
		
	}
	

	public List<ItemMobile> selectitems(Integer categoryId,Integer vendorId) throws Exception{
		List<ItemMobile> result =new ArrayList<ItemMobile>() ;
	
		try {
			session = sessionFactory.openSession();
			
			String sqlquery ="SELECT item.id,item.Name,item.Price,item.count,vendor.Name FROM Item as item"
					+ ",Vendor as vendor where item.CategoryID="+categoryId+" and item.VendorID="+vendorId; 
		
		Query query = session.createQuery(sqlquery);
		
		result = query.list();
	
			
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
	
	public void processOrder(User updateduser,List<Item> modifieditemlist,Order order) throws Exception{
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			for(Item item: modifieditemlist){
				session.merge(item);
			}
			session.merge(updateduser);
			session.save(order);
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
	
}
