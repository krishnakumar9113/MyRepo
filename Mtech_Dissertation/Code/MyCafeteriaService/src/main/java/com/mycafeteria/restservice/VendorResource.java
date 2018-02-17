package com.mycafeteria.restservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import jersey.repackaged.com.google.common.collect.Lists;

import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.Location;
import com.mycafeteria.bean.User;
import com.mycafeteria.bean.UserMobile;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.bean.VendorMobile;
import com.mycafeteria.businesslogic.LocationFactory;
import com.mycafeteria.businesslogic.OrderFactory;
import com.mycafeteria.businesslogic.UtilityFactory;
import com.mycafeteria.businesslogic.VendorFactory;
import com.mycafeteria.utility.SendMail;
import com.mycafeteria.utility.Utility;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Path("/vendor")
public class VendorResource {

	@Context
	private HttpServletRequest httpRequest;
	String type=MediaType.APPLICATION_JSON;
	CacheControl cc;

	VendorFactory vendorFactory ;
	UtilityFactory utilityfactory;
	LocationFactory locationfactory;
	OrderFactory orderfactory;
	public VendorResource(@Context HttpHeaders headers)
	{
		
		cc = new CacheControl();
		cc.setNoCache(true);
        cc.setNoStore(true);
        cc.setMustRevalidate(true);
		
        vendorFactory = new VendorFactory();
        utilityfactory= new UtilityFactory();
        locationfactory= new LocationFactory();
        orderfactory=new OrderFactory ();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public Response getVendors() {
		try{
			List<Vendor> vendors=vendorFactory.getModel();
			
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
			for(Vendor ven:vendors){
				ven.setPassword("*****");
				ven.setLocationName(locationfactory.getModelById(ven.getLocationID()).getLocation());
			}
		
				GenericEntity<List<Vendor> > entity = 
					       new GenericEntity<List<Vendor>>(Lists.newArrayList(vendors)) {};
			return Response.ok(entity,type).cacheControl(cc).build();
		
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVendorById(@PathParam("id") int id) {
		
		try{
			if (id == 0) {
				return Response.status(400).cacheControl(cc).entity("id is null").build();
			} else {
				if(httpRequest.getContentType()!=null){
					type=httpRequest.getContentType();
				}
			
				Vendor vendor=vendorFactory.getModelById(id);
				
				if(vendor!=null){
					vendor.setPassword("*****");
					vendor.setLocationName(locationfactory.getModelById(vendor.getLocationID()).getLocation());
					
					return Response.ok(vendor,type).cacheControl(cc).build();
				}else{
					return Response.status(200).cacheControl(cc).entity(vendor).build();
				}
			}
		
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
		
	}
	
	
	@GET
	@Path("/mobile/filter")
	@Produces(MediaType.APPLICATION_JSON )
	public Response getVendorMobile(@Context UriInfo  queryParams)
	{		
		MultivaluedMap<String,String> filterBy = queryParams.getQueryParameters();
		if (filterBy.size() == 0) {
			return Response.status(400).cacheControl(cc)
					.entity("No filter parameters are passed").build();
		} else {
		try{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
		List<Vendor> vendors=vendorFactory.getModelWithFilter(filterBy);
		List<VendorMobile> vendorMobileBeanList= new ArrayList<VendorMobile>();
		for(Vendor vendor:vendors){
			
			vendorMobileBeanList.add(new VendorMobile(vendor.getId(),vendor.getName()) );
		}
			GenericEntity<List<VendorMobile>> entity = 
				       new GenericEntity<List<VendorMobile>>(Lists.newArrayList(vendorMobileBeanList)) {};
		
			return Response.ok(entity,type).cacheControl(cc).build();
				
	}catch(ClassCastException e1)
		{
		return Response.status(400).cacheControl(cc).entity("DataType Mismatch").build();
		}
		catch(Exception e)
		   { 
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	       }
		
		}
	}
	
	@GET
	@Path("/mobile/CatVen/filter/{CategoryID}/{LocationID}")
	@Produces(MediaType.APPLICATION_JSON )
	public Response GetCatVendorMobile(@PathParam("CategoryID") int categoryID,@PathParam("LocationID") int locationId)
	{		
		
		try{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
			List<VendorMobile> vendorMobileList=utilityfactory.selectVendorsByCategoryIdAndLocationId(categoryID,locationId);
	
			GenericEntity<List<VendorMobile>> entity = 
				       new GenericEntity<List<VendorMobile>>(Lists.newArrayList(vendorMobileList)) {};
	
			return Response.ok(entity,type).cacheControl(cc).build();
		
	}catch(ClassCastException e1)
		{
		return Response.status(400).cacheControl(cc).entity("DataType Mismatch").build();
		}
		catch(Exception e)
		   { 
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	       }
		
		}

	
	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON )
	public Response getVendorFilter(@Context UriInfo  queryParams)
	{		
		MultivaluedMap<String,String> filterBy = queryParams.getQueryParameters();
		if (filterBy.size() == 0) {
			return Response.status(400).cacheControl(cc)
					.entity("No filter parameters are passed").build();
		} else {
		try{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
		List<Vendor> vendors=vendorFactory.getModelWithFilter(filterBy);
			GenericEntity<List<Vendor> > entity = 
				       new GenericEntity<List<Vendor>>(Lists.newArrayList(vendors)) {};
		//	if(appsobj!=null){
			return Response.ok(entity,type).cacheControl(cc).build();
		/*	}else{
				return Response.status(404).cacheControl(cc).entity("No Records Found").build();
			}*/
		
	}catch(ClassCastException e1)
		{
		return Response.status(400).cacheControl(cc).entity("DataType Mismatch").build();
		}
		catch(Exception e)
		   { 
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	       }
		
		}
	}
	
	
	
	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateVendor(String vendorJson) {
		try{
		if((vendorJson ==null)){
			return Response.status(400).cacheControl(cc).entity("Vendor is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
			JSONObject result=new JSONObject();
			JSONObject jsonVendorObject = new JSONObject(vendorJson);
			JSONObject status=new JSONObject();
			String username=jsonVendorObject.optString("username").toString();
			String password=jsonVendorObject.optString("password").toString();
		
				//vendor
				Vendor vendor =vendorFactory.authenticate(username);
				if(vendor==null){
					status.put("fail", "Invalid Vendor MailID");
					return Response.status(200).entity(status.toString()).type(type).cacheControl(cc).build();
				}
				if (vendor.getPassword().equals(password)){
					//resp.put("id", Vendorobj.getId());
					result.put("isadmin", false);
					result.put("id", vendor.getId());
					result.put("name", vendor.getName());
					result.put("location", locationfactory.getModelById(vendor.getLocationID()).getLocation());
					status.put("success", result);
					return Response.status(200).entity(status.toString()).type(type).cacheControl(cc).build();
				
			}else{
				status.put("fail", "Invalid UserName and Password");
				return Response.status(200).entity(status.toString()).type(type).cacheControl(cc).build();
			}
			
		
		
		//	}
		}

	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
		}

	@POST
	@Path("/changepwd")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response updatePassword(String password) {
		try{
		if((password ==null)||(password.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("pwd is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject vendorJsonObject = new JSONObject(password);
		
		Vendor vendor =vendorFactory.getModelById(vendorJsonObject.optInt("vendorid"));
		if (vendor.getPassword().equals(vendorJsonObject.optString("currentpwd"))){
			vendor.setPassword(vendorJsonObject.optString("newpwd"));
			vendorFactory.put(vendor);
			 return Response.status(200).entity("Password changed successfully").type(type).cacheControl(cc).build();
		}else{
			return Response.status(200).cacheControl(cc).entity("Invalid Username and password").build();
		}
		
		
		}	
			
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postVendor(String vendorjson) {
		try{
		if((vendorjson ==null)){
			return Response.status(400).cacheControl(cc).entity("Vendor is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Genson genson=new GensonBuilder().useDateFormat(format).create();
		
		Vendor vendor = genson.deserialize(vendorjson, Vendor.class);
		if(vendorFactory.getModelByMailID(vendor.getMail()).size()==0){
			String randompassword=Utility.randomString(8);
			
			vendor.setPassword(randompassword);
		vendorFactory.post(vendor);
		// SendMail.sendmail(Vendorobj.getMail(),randompass);
		 (new Thread(new SendMail(vendor.getMail(),randompassword))).start();
		return Response.status(201).entity("Vendor created successfully.Password has been sent to your mailid.Kindly change during next login").type(type).cacheControl(cc).build();
		}else{
			return Response.status(200).entity("Vendor MailID already registered").type(type).cacheControl(cc).build();
		}
		
		}
	}/*catch(org.hibernate.exception.ConstraintViolationException ex){
		return Response.status(409).cacheControl(cc).entity("Record Already Exists").build();
	}*/catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	@POST
	@Path("/forgotpwd")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response forgotpass(String mailid) {
		try{
		if((mailid ==null)||(mailid.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("mailid is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject jsonVendorObject = new JSONObject(mailid);
		
			List<Vendor> vendorList =vendorFactory.getModelByMailID(jsonVendorObject.optString("mailid"));
			
		if(vendorList.size()==0){
			 return Response.status(200).entity("E mail Id not registered").type(type).cacheControl(cc).build();
		}else{
			Vendor vendor=vendorList.get(0);
			String randompass=Utility.randomString(8);
			// SendMail.sendmail(obj.getMail(),randompass);
			 (new Thread(new SendMail(vendor.getMail(),randompass))).start();
			 vendor.setPassword(randompass);
			 vendorFactory.put(vendor);
				 return Response.status(200).entity("New password has been sent to your registered mailid").type(type).cacheControl(cc).build();
			
		}
		//send mail with random pass  
			}	
			
	}catch(Exception e){
		 return Response.status(200).entity("E mail Id not found").type(type).cacheControl(cc).build();
	}
	}
	
	@PUT
	@Path("/isactive")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response putVendorAsActive(String vendorJson) {
		try{
		if((vendorJson ==null)||(vendorJson.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("Location is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Genson genson=new GensonBuilder().useDateFormat(format).create();
			JSONObject vendorJsonObject = new JSONObject(vendorJson);
		
		Vendor vendor =vendorFactory.getModelById(vendorJsonObject.optInt("vendorid"));

		vendor.setIsActive(vendorJsonObject.optBoolean("isactive"));		
		vendorFactory.put(vendor);
		 return Response.status(200).entity("Updated Successfully").type(type).cacheControl(cc).build();
		}
	}
	catch(org.hibernate.exception.ConstraintViolationException ex){
		return Response.status(200).cacheControl(cc).entity("MailID already Exists").build();
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	@PUT
	//@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response putVendor(String vendorJson) {
		try{
		if((vendorJson ==null)||(vendorJson.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("Location is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Genson genson=new GensonBuilder().useDateFormat(format).create();
		Vendor venobj = genson.deserialize(vendorJson, Vendor.class);
			Vendor vendor =	vendorFactory.getModelById(venobj.getId());
			if(vendor!=null){
				vendor.setLocationID(venobj.getLocationID());
				vendor.setName(venobj.getName());
				vendorFactory.put(vendor);
				 return Response.status(200).entity("Updated Successfully").type(type).cacheControl(cc).build();
			}else{
				return Response.status(200).cacheControl(cc).entity("MailID already Exists").build();	
			}
			
		}
	}
	catch(org.hibernate.exception.ConstraintViolationException ex){
		return Response.status(500).cacheControl(cc).entity("MailID already Exists").build();
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}

	
	@DELETE
	@Path("/{id}")
	public Response DeleteVendor(@PathParam("id") int id) {
		try{
			if(id==0){
				return Response.status(400).cacheControl(cc).entity("id is null").build();
			}else{
				Vendor vendor=	vendorFactory.getModelById(id);
			if (vendor==null)
				return Response.status(200).entity("No Records Found for Deletion").cacheControl(cc)
						.build();
			else {
				if(orderfactory.getModelByVendor(vendor.getId()).size()==0){
					vendorFactory.deleteVendor(id);		
					return Response.status(200).cacheControl(cc).entity("Deleted Successfully").build();
				
				}else{
					return Response.status(200).cacheControl(cc).entity("Cannot delete vendor with paid/printed orders .Kindly delete the vendor once order is expired.").build();	
				}
				}
			}
		}/*catch(org.hibernate.ObjectNotFoundException e){
			return Response.status(404).cacheControl(cc).entity("Not Found").build();
		}*/catch(Exception e){
			return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
		}
	}
}
