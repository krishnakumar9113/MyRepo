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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jersey.repackaged.com.google.common.collect.Lists;

import org.json.JSONObject;

import com.mycafeteria.bean.Item;
import com.mycafeteria.bean.Order;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.businesslogic.CategoryFactory;
import com.mycafeteria.businesslogic.ItemFactory;
import com.mycafeteria.businesslogic.LocationFactory;
import com.mycafeteria.businesslogic.OrderFactory;
import com.mycafeteria.businesslogic.UtilityFactory;
import com.mycafeteria.businesslogic.VendorFactory;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

@Path("/item")
public class ItemResource {

	@Context
	private HttpServletRequest httpRequest;
	String type = MediaType.APPLICATION_JSON;
	CacheControl cc;

	ItemFactory itemFactory;
	UtilityFactory utilityfactory;
	VendorFactory vendorfactory;
	CategoryFactory categoryfactory;
	OrderFactory orderfactory;
	LocationFactory locationfactory;

	public ItemResource(@Context HttpHeaders headers) {

		cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);

		itemFactory = new ItemFactory();
		utilityfactory = new UtilityFactory();
		vendorfactory = new VendorFactory();
		categoryfactory = new CategoryFactory();
		orderfactory = new OrderFactory();
		locationfactory = new LocationFactory();
	}

	@GET
	@Path("/vendor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetItemsbyvendorid(@PathParam("id") int id) {
		try {
			List<Item> itemlist = itemFactory.getModelByVendorId(id);
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}
			for (Item item : itemlist) {

				item.setCategoryName(categoryfactory.getModelById(
						item.getCategoryID()).getCategoryName());
			}

			GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(
					Lists.newArrayList(itemlist)) {
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
	public Response getItemById(@PathParam("id") int id) {

		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				Item item = itemFactory.getModelById(id);
				if (item != null) {
					return Response.ok(item, type).cacheControl(cc).build();
				} else {
					return Response.status(200).cacheControl(cc).entity(item)
							.build();
				}
			}

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemFilter(@Context UriInfo queryParams) {
		MultivaluedMap<String, String> filterBy = queryParams
				.getQueryParameters();
		if (filterBy.size() == 0) {
			return Response.status(400).cacheControl(cc)
					.entity("No filter parameters are passed").build();
		} else {
			try {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				List<Item> items = itemFactory.getModelWithFilter(filterBy);
				for (Item item : items) {

					Vendor vendor = vendorfactory.getModelById(item
							.getVendorID());
					item.setVendorName(vendor.getName()
							+ " - "
							+ locationfactory.getModelById(
									vendor.getLocationID()).getCode());
					// item.setVendorName(vendorfactory.getModelById(item.getVendorID()).getName());
				}
				GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(
						Lists.newArrayList(items)) {
				};

				return Response.ok(entity, type).cacheControl(cc).build();

			} catch (ClassCastException e1) {
				return Response.status(400).cacheControl(cc)
						.entity("DataType Mismatch").build();
			} catch (Exception e) {
				return Response.status(500).cacheControl(cc)
						.entity("Internal Server Error").build();
			}

		}
	}

	@PUT
	@Path("/isactive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putItemAsActive(String Item) {
		try {
			if ((Item == null) || (Item.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Item is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();
				JSONObject itemjson = new JSONObject(Item);

				Item item = itemFactory.getModelById(itemjson.optInt("itemid"));

				item.setIsActive(itemjson.optBoolean("isactive"));
				itemFactory.put(item);
				return Response.status(200).entity("Updated Successfully")
						.type(type).cacheControl(cc).build();
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Post(String itemjson) {
		try {
			if ((itemjson == null)) {
				return Response.status(400).cacheControl(cc)
						.entity("Vendor is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();

				Item item = genson.deserialize(itemjson, Item.class);

				itemFactory.post(item);

				return Response.status(201).entity("Saved successfully")
						.type(type).cacheControl(cc).build();

			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Put(String itemjson) {
		try {
			if ((itemjson == null) || (itemjson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("item is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();
				Item item = genson.deserialize(itemjson, Item.class);
				Item updateditem = itemFactory.getModelById(item.getId());
				updateditem.setCategoryID(item.getCategoryID());
				updateditem.setCount(item.getCount());
				updateditem.setPrice(item.getPrice());
				updateditem.setName(item.getName());
				itemFactory.put(updateditem);
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
	public Response DeleteItem(@PathParam("id") int id) {
		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				Item item = itemFactory.getModelById(id);

				if (item == null)
					return Response.status(200)
							.entity("No Records Found for Deletion")
							.cacheControl(cc).build();
				else {
					List<Order> orders = orderfactory.getModelByVendorpaid(
							item.getVendorID(), item.getId());
					if (orders.size() == 0) {

						itemFactory.delete(id);
						// delete related orders
						orderfactory.deleteItemsByVendorExpired(
								item.getVendorID(), item.getId());

						return Response.status(200).cacheControl(cc)
								.entity("Deleted Successfully").build();
					} else {
						return Response
								.status(200)
								.cacheControl(cc)
								.entity("Cannot delete item in paid/printed orders.Kindly delete the item once order is expired.")
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
