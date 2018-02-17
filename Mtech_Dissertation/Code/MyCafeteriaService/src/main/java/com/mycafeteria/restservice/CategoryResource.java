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

import com.mycafeteria.bean.Category;
import com.mycafeteria.bean.Item;
import com.mycafeteria.businesslogic.CategoryFactory;
import com.mycafeteria.businesslogic.ItemFactory;
import com.mycafeteria.businesslogic.OrderFactory;
import com.mycafeteria.businesslogic.UtilityFactory;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

@Path("/category")
public class CategoryResource {

	@Context
	private HttpServletRequest httpRequest;
	String type = MediaType.APPLICATION_JSON;
	CacheControl cc;

	CategoryFactory categoryFactory;
	UtilityFactory utilityFactory;
	OrderFactory orderFactory;
	ItemFactory itemFactory;

	public CategoryResource(@Context HttpHeaders headers) {

		cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);

		categoryFactory = new CategoryFactory();
		utilityFactory = new UtilityFactory();
		orderFactory = new OrderFactory();
		itemFactory = new ItemFactory();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetCategories() {
		try {
			List<Category> categories = categoryFactory.getModel();
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			GenericEntity<List<Category>> entity = new GenericEntity<List<Category>>(
					Lists.newArrayList(categories)) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/location/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoriesByLocationId(@PathParam("id") int locationid) {
		try {
			List<Category> categories = utilityFactory
					.selectCategoriesByLocationId(locationid);
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			GenericEntity<List<Category>> entity = new GenericEntity<List<Category>>(
					categories) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetbyID(@PathParam("id") int id) {

		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				Category category = categoryFactory.getModelById(id);
				if (category != null) {
					return Response.ok(category, type).cacheControl(cc).build();
				} else {
					return Response.status(200).cacheControl(cc)
							.entity(category).build();
				}
			}

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Post(String categoryJson) {
		try {
			if ((categoryJson == null) || (categoryJson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Category is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();

				Category category = genson.deserialize(categoryJson,
						Category.class);

				categoryFactory.post(category);

				return Response.status(201).entity("Created Successfully")
						.type(type).cacheControl(cc).build();

			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response PutCategory(String categoryJson) {
		try {
			if ((categoryJson == null) || (categoryJson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Category is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();
				Category category = genson.deserialize(categoryJson,
						Category.class);

				categoryFactory.put(category);
				return Response.status(200).entity("Saved Successfully")
						.type(type).cacheControl(cc).build();
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response Delete(@PathParam("id") int id) {
		try {
			if (id == 0) {
				return Response.status(400).cacheControl(cc)
						.entity("id is null").build();
			} else {
				Category category = categoryFactory.getModelById(id);
				if (category == null)
					return Response.status(200)
							.entity("No Records Found for Deletion")
							.cacheControl(cc).build();
				else {
					boolean enabledel = true;
					List<Item> itemsUnderCategory = itemFactory
							.getModelByCategoryId(id);
					for (Item item : itemsUnderCategory) {
						if (orderFactory.getModelByitemspaid(item.getId())
								.size() != 0) {
							enabledel = false;
							break;
						}
					}
					if (enabledel) {
						categoryFactory.delete(id);
						// delete orders related to items under that category
						for (Item item : itemsUnderCategory) {
							orderFactory
									.deleteModelByItemsExpired(item.getId());

						}
						return Response.status(200).cacheControl(cc)
								.entity("Deleted Successfully").build();
					} else {

						return Response
								.status(200)
								.cacheControl(cc)
								.entity("Cannot delete Category with Items having paid/printed orders.Kindly delete the category once orders are expired.")
								.build();

					}

				}
			}
		}/*
		 * catch(org.hibernate.ObjectNotFoundException e){ return
		 * Response.status(404).cacheControl(cc).entity("Not Found").build(); }
		 */catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/mobile/VenCat/filter/{VendorID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetVenCategoryMobile(@PathParam("VendorID") int vendorid) {

		try {
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}
			List<Category> categories = utilityFactory
					.selectCategoriesByvenidLocid(vendorid);

			GenericEntity<List<Category>> entity = new GenericEntity<List<Category>>(
					Lists.newArrayList(categories)) {
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
