package com.mycafeteria.restservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.json.JSONArray;
import org.json.JSONObject;

import jersey.repackaged.com.google.common.collect.Lists;

import com.mycafeteria.bean.Category;
import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.ItemMobile;
import com.mycafeteria.bean.User;
import com.mycafeteria.bean.UserMobile;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.businesslogic.ItemFactory;
import com.mycafeteria.businesslogic.LocationFactory;
import com.mycafeteria.businesslogic.UserFactory;
import com.mycafeteria.businesslogic.UtilityFactory;
import com.mycafeteria.businesslogic.VendorFactory;
import com.mycafeteria.utility.SendMail;
import com.mycafeteria.utility.Utility;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

@Path("/user")
public class UserResource {

	@Context
	private HttpServletRequest httpRequest;
	String type=MediaType.APPLICATION_JSON;
	CacheControl cc;
	ItemFactory itemFactory;
	UserFactory userFactory ;
	UtilityFactory  utilityFactory;
	VendorFactory vendorfactory;
	LocationFactory locationfactory;
	public UserResource(@Context HttpHeaders headers)
	{
		
		cc = new CacheControl();
		cc.setNoCache(true);
        cc.setNoStore(true);
        cc.setMustRevalidate(true);
		
        userFactory = new UserFactory();
		utilityFactory= new UtilityFactory();
		itemFactory= new ItemFactory();
		vendorfactory= new VendorFactory();
		locationfactory=new LocationFactory();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public Response getUsers() {
		try{
			List<User> users=userFactory.getModel();
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
				GenericEntity<List<User> > entity = 
					       new GenericEntity<List<User>>(Lists.newArrayList(users)) {};
			return Response.ok(entity,type).cacheControl(cc).build();
	
		
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetbyID(@PathParam("id") int id) {
		
		try{
			if (id == 0) {
				return Response.status(400).cacheControl(cc).entity("id is null").build();
			} else {
				if(httpRequest.getContentType()!=null){
					type=httpRequest.getContentType();
				}
			
				User user=userFactory.getModelById(id);
				if(user!=null){
					return Response.ok(user,type).cacheControl(cc).build();
				}else{
					return Response.status(200).cacheControl(cc).entity(user).build();
				}
			}
		
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
		
	}
	
	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON )
	public Response getUserFilter(@Context UriInfo  queryParams)
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
		
			List<User> users=userFactory.getModelWithFilter(filterBy);
			if(users!=null){
			return Response.ok(users.get(0),type).cacheControl(cc).build();
			}else{
				return Response.status(404).cacheControl(cc).entity("No Records Found").build();
			}
		
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
	@Path("/mobile/Auth")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Authenticate(String userJson) {
		try{
		if((userJson ==null)||(userJson.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("User is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
			
			JSONObject jsonUserObject = new JSONObject(userJson);
		List<User> userslist	=userFactory.authenticate(jsonUserObject.optString("username").toString());
		User user =null ;
if(userslist.size()!=0){
	user=userslist.get(0);
}else{
	return Response.status(200).cacheControl(cc).entity("UserName not found").type(type).build();
}
		if (user.getPassword().equals(jsonUserObject.optString("password").toString())){
			
			//generate auth-token
			String auth_token=Utility.randomString(12);
			user.setAuth_token(auth_token);
			userFactory.put(user);
			
			UserMobile usermobilebean= new UserMobile();
			usermobilebean.setUser(user);
			usermobilebean.getUser().setPassword("******");
			List<Item> itemslist= new ArrayList<Item>();
			if(user.getFavs()!=null){
				for(String eachitemid :user.getFavs().split(",")){
					Item item=itemFactory.getModelById(Integer.parseInt(eachitemid));
					if(item!=null&&item.getCount()!=0){
						Vendor vendor=vendorfactory.getModelById(item.getVendorID());
						item.setVendorName(vendor.getName()+" - "+locationfactory.getModelById(vendor.getLocationID()).getCode());
						itemslist.add(item);
					}
					
				}
			}
			usermobilebean.setFavs(itemslist);
			return Response.status(200).entity(usermobilebean).type(type).cacheControl(cc).build();
		}else{
			return Response.status(200).cacheControl(cc).entity("Invalid UserName and Password").type(type).build();
		}
			
	
		
		}
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Server Error").build();
	}
	}
	

	@POST
	@Path("/mobile/Reg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUser(String userJson) {
		try{
		if((userJson ==null)||(userJson.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("User is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
			JSONObject jsonUserObject = new JSONObject(userJson);
			List<User> userslist	=userFactory.authenticate(jsonUserObject.optString("username").toString());
	if(userslist.size()!=0){
		return Response.status(200).cacheControl(cc).entity("User already registered").type(type).build();
	}
	
	List<User> Userlist =userFactory.checkUserWithMailID(jsonUserObject.optString("mailid"));	
	if(Userlist.size()!=0){
		return Response.status(200).cacheControl(cc).entity("Mailid already registered").type(type).build();
	}
	
		userFactory.post(new User(1,jsonUserObject.getString("username"),jsonUserObject.getString("password"),0.00,1000.00,
				jsonUserObject.getString("mailid")));
		
		return Response.status(201).entity("OK").type(type).cacheControl(cc).build();
		
		}
	}/*catch(org.hibernate.exception.ConstraintViolationException ex){
		return Response.status(409).cacheControl(cc).entity("Record Already Exists").build();
	}*/catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	@POST
	@Path("/favs")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response Putfavs(String Favorites) {
		try{
		if((Favorites ==null)||(Favorites.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("User is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject jsonUserObject = new JSONObject(Favorites);
			
		User Userobj =userFactory.getModelById(jsonUserObject.optInt("userid"));
		Userobj.setFavs(jsonUserObject.optString("favorites"));
		userFactory.put(Userobj);
		 return Response.status(200).entity("OK").type(type).cacheControl(cc).build();
		}	
			
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	@POST
	@Path("/changepwd")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response updatepass(String pwd) {
		try{
		if((pwd ==null)||(pwd.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("pwd is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject jsonUserObject = new JSONObject(pwd);
		
		User Userobj =userFactory.getModelById(jsonUserObject.optInt("userid"));
		if (Userobj.getPassword().equals(jsonUserObject.optString("currentpwd"))){
			Userobj.setPassword(jsonUserObject.optString("newpwd"));
			userFactory.put(Userobj);
			 return Response.status(200).entity("OK").type(type).cacheControl(cc).build();
		}else{
			return Response.status(400).cacheControl(cc).entity("Invalid Username and password").build();
		}
		
		
		}	
			
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	@POST
	@Path("/mobile/forgotpwd")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response forgotpass(String mailid) {
		try{
		if((mailid ==null)||(mailid.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("mailid is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject jsonUserObject = new JSONObject(mailid);
			List<User> userlist =userFactory.checkUserWithMailID(jsonUserObject.optString("mailid"));
			User user =null ;
			if(userlist.size()!=0){
				user=userlist.get(0);
			}else{
				return Response.status(200).cacheControl(cc).entity("MailId not found").type(type).build();
			}
		
		//send mail with random pass  
		String randompass=Utility.randomString(8);
		 (new Thread(new SendMail(user.getmailid(),randompass))).start();
	// SendMail.sendmail(Userobj.getmailid(),randompass);
		user.setPassword(randompass);
		userFactory.put(user);
		 return Response.status(200).entity("Ok").type(type).cacheControl(cc).build();
		}	
			
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	
	
	@POST
	@Path("/declamt")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response updateDeclaredAmount(String amount) {
		try{
		if((amount ==null)||(amount.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("pwd is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		
			JSONObject jsonUserObject = new JSONObject(amount);
		
		User user =userFactory.getModelById(jsonUserObject.optInt("userid"));
		
			user.setDeclaredAmount(jsonUserObject.optDouble("declamt"));
			userFactory.put(user);
			 return Response.status(200).entity("OK").type(type).cacheControl(cc).build();
			 }	
			
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	
	@PUT
	//@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response PutLocation(String userJson) {
		try{
		if((userJson ==null)||(userJson.isEmpty())){
			return Response.status(400).cacheControl(cc).entity("User is null").build();
		}else{
			if(httpRequest.getContentType()!=null){
				type=httpRequest.getContentType();
			}
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Genson genson=new GensonBuilder().useDateFormat(format).create();
		User user = genson.deserialize(userJson, User.class);
		
		userFactory.put(user);
		 return Response.status(200).entity(user).type(type).cacheControl(cc).build();
		}
	}catch(Exception e){
		return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
	}
	}
	
	
	@DELETE
	@Path("/{id}")
	public Response Delete(@PathParam("id") int id) {
		try{
			if(id==0){
				return Response.status(400).cacheControl(cc).entity("id is null").build();
			}else{
				User user=userFactory.getModelById(id);
			if (user==null)
				return Response.status(200).entity("No Records Found for Deletion").cacheControl(cc)
						.build();
			else {
				userFactory.delete(id);		
				return Response.status(200).cacheControl(cc).entity("OK").build();
			}
			}
		}catch(Exception e){
			return Response.status(500).cacheControl(cc).entity("Internal Server Error").build();
		}
	}
	
	
}
