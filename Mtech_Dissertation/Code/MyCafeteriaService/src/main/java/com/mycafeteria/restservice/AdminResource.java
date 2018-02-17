package com.mycafeteria.restservice;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.mycafeteria.bean.Admin;
import com.mycafeteria.businesslogic.AdminFactory;

@Path("/admin")
public class AdminResource {

	@Context
	private HttpServletRequest httpRequest;
	String type = MediaType.APPLICATION_JSON;
	CacheControl cc;

	AdminFactory adminFactory;

	public AdminResource(@Context HttpHeaders headers) {

		cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);

		adminFactory = new AdminFactory();

	}

	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(String adminJson) {
		try {
			if ((adminJson == null)) {
				return Response.status(400).cacheControl(cc)
						.entity("Admin is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				JSONObject resp = new JSONObject();
				JSONObject jsonUserObject = new JSONObject(adminJson);
				JSONObject status = new JSONObject();
				String username = jsonUserObject.optString("username")
						.toString();
				String password = jsonUserObject.optString("password")
						.toString();
				
				// vendor
				Admin admin = adminFactory.authenticate(username);
				if (admin == null) {
					status.put("fail", "Invalid Admin MailID");
					return Response.status(200).entity(status.toString())
							.type(type).cacheControl(cc).build();
				}
				if (admin.getPassword().equals(password)) {
					// resp.put("id", Vendorobj.getId());
					resp.put("isadmin", true);
					status.put("success", resp);
					return Response.status(200).entity(status.toString())
							.type(type).cacheControl(cc).build();

				} else {
					status.put("fail", "Invalid UserName and Password");
					return Response.status(200).entity(status.toString())
							.type(type).cacheControl(cc).build();
				}

			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

}
