package com.mycafeteria.restservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.core.Response;

import jersey.repackaged.com.google.common.collect.Lists;

import com.mycafeteria.bean.Location;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.businesslogic.LocationFactory;
import com.mycafeteria.businesslogic.OrderFactory;
import com.mycafeteria.businesslogic.VendorFactory;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

@Path("/location")
public class LocationResource {

	@Context
	private HttpServletRequest httpRequest;
	String type = MediaType.APPLICATION_JSON;
	CacheControl cc;

	LocationFactory locationFactory;
	VendorFactory vendorfactory;
	OrderFactory orderfactory;

	public LocationResource(@Context HttpHeaders headers) {

		cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);

		locationFactory = new LocationFactory();
		vendorfactory = new VendorFactory();
		orderfactory = new OrderFactory();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetLocations() {
		try {
			List<Location> locations = locationFactory.getModel();
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			GenericEntity<List<Location>> entity = new GenericEntity<List<Location>>(
					Lists.newArrayList(locations)) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocationByID(@PathParam("id") int id) {

		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				Location location = locationFactory.getModelById(id);
				if (location != null) {
					return Response.ok(location, type).cacheControl(cc).build();
				} else {
					return Response.status(200).cacheControl(cc)
							.entity(location).build();
				}
			}

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(String locationJson) {
		try {
			if ((locationJson == null) || (locationJson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Location is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();

				Location location = genson.deserialize(locationJson,
						Location.class);
				if (locationFactory.getModelbyCode(location.getCode()).size() == 0) {
					locationFactory.postLocation(location);

					return Response.status(201).entity("Created Successfully")
							.type(type).cacheControl(cc).build();
				} else {
					return Response.status(201)
							.entity("Location Code already exist").type(type)
							.cacheControl(cc).build();
				}

			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response PutLocation(String locationJson) {
		try {
			if ((locationJson == null) || (locationJson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Location is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();
				Location location = genson.deserialize(locationJson,
						Location.class);

				locationFactory.putLocationModel(location);
				return Response.status(200).entity("Updated successfully")
						.type(type).cacheControl(cc).build();
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response DeleteLocation(@PathParam("id") int id) {
		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				Location location = locationFactory.getModelById(id);
				if (location == null)
					return Response.status(200)
							.entity("No Records Found for Deletion")
							.cacheControl(cc).build();
				else {
					boolean enabledel = true;
					for (Vendor vendor : vendorfactory.getModelByLocationId(id)) {
						if (orderfactory.getModelByVendor(vendor.getId())
								.size() != 0) {
							enabledel = false;
							break;
						}
					}
					if (enabledel) {
						locationFactory.deleteLocation(id);
						return Response.status(200).cacheControl(cc)
								.entity("Deleted Successfully").build();

					} else {
						return Response
								.status(200)
								.cacheControl(cc)
								.entity("Cannot delete Location with vendors having paid/printed orders .Kindly delete the location once orders are expired.")
								.build();

					}
				}
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}
}
