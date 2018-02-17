package com.mycafeteria.restservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.mycafeteria.bean.User;
import com.mycafeteria.bean.Vendor;
import com.mycafeteria.businesslogic.ItemFactory;
import com.mycafeteria.businesslogic.LocationFactory;
import com.mycafeteria.businesslogic.OrderFactory;
import com.mycafeteria.businesslogic.UserFactory;
import com.mycafeteria.businesslogic.UtilityFactory;
import com.mycafeteria.businesslogic.VendorFactory;
import com.mycafeteria.service.bean.ItemCountBean;
import com.mycafeteria.service.bean.OrderPostBean;
import com.mycafeteria.service.bean.OrderVendorBean;
import com.mycafeteria.service.bean.ServiceItemBean;
import com.mycafeteria.service.bean.ServiceOrderBean;
import com.mycafeteria.service.bean.ServiceOrderListBean;
import com.mycafeteria.utility.Utility;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

@Path("/order")
public class OrderResource {

	@Context
	private HttpServletRequest httpRequest;
	String type = MediaType.APPLICATION_JSON;
	CacheControl cc;

	OrderFactory orderFactory;
	ItemFactory itemFactory;
	VendorFactory vendorfactory;
	UserFactory userfactory;
	LocationFactory locationfactory;
	UtilityFactory utilfactory;

	public OrderResource(@Context HttpHeaders headers) {

		cc = new CacheControl();
		cc.setNoCache(true);
		cc.setNoStore(true);
		cc.setMustRevalidate(true);

		orderFactory = new OrderFactory();
		itemFactory = new ItemFactory();
		vendorfactory = new VendorFactory();
		userfactory = new UserFactory();
		locationfactory = new LocationFactory();
		utilfactory = new UtilityFactory();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetOrders() {
		try {
			List<ServiceOrderBean> ServiceOrderBeanList = new ArrayList<ServiceOrderBean>();
			List<Order> orderlist = orderFactory.getModel();
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			for (Order order : orderlist) {
				// item.setVendorName(ven.getName()+"-"+locfactory.getModelById(ven.getLocationID()).getCode());
				Vendor vendor = vendorfactory.getModelById(order.getVendorID());
				String vendorName = vendor.getName()
						+ " - "
						+ locationfactory.getModelById(vendor.getLocationID())
								.getCode();
				List<ServiceItemBean> serviceitemlist = new ArrayList<ServiceItemBean>();

				String username = userfactory.getModelById(order.getUserid())
						.getUserName();
				String items_counts = order.getItems_count();

				for (String eachitem : items_counts.split(",")) {

					String[] item_count = eachitem.split(":");
					Item item = itemFactory.getModelById(Integer
							.parseInt(item_count[0]));
					if (item != null) {
						String itemname = item.getName();
						Double itemprice = item.getPrice();
						ServiceItemBean serviceitem = new ServiceItemBean(
								itemname, Integer.parseInt(item_count[1]),
								itemprice);
						serviceitemlist.add(serviceitem);
					}
				}
				ServiceOrderBean serviceorderbean = new ServiceOrderBean(
						order.getId(), order.getAmount(), serviceitemlist,
						order.getSecretCode(), username, vendorName,
						order.getStatus(), order.getTimeStamp());

				ServiceOrderBeanList.add(serviceorderbean);

			}

			GenericEntity<List<ServiceOrderBean>> entity = new GenericEntity<List<ServiceOrderBean>>(
					Lists.newArrayList(ServiceOrderBeanList)) {
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
	public Response getbyOrderID(@PathParam("id") int id) {

		try {

			Order order = orderFactory.getModelById(id);
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			return Response.ok(getOrderDetails(order), type).cacheControl(cc)
					.build();

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

	@GET
	@Path("/bill/secretcode/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderBySecretCode(@PathParam("code") String Code) {

		try {
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}
			List<Order> order = orderFactory.getModelBySecretCode(Code);
			if (order.size() != 0) {

				return Response.ok(getOrderDetails(order.get(0)), type)
						.cacheControl(cc).build();
			} else {
				return Response.ok("Invalid Code", type).cacheControl(cc)
						.build();
			}

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}

	}

	//Getting order details
	public ServiceOrderBean getOrderDetails(Order order) {

		Vendor vendor = vendorfactory.getModelById(order.getVendorID());
		String vendorname = vendor.getName() + " - "
				+ locationfactory.getModelById(vendor.getLocationID()).getCode();
		List<ServiceItemBean> serviceitemlist = new ArrayList<ServiceItemBean>();

		String username = userfactory.getModelById(order.getUserid())
				.getUserName();
		String items_counts = order.getItems_count();

		for (String eachitem : items_counts.split(",")) {

			String[] item_count = eachitem.split(":");
			Item item = itemFactory.getModelById(Integer
					.parseInt(item_count[0]));
			if (item != null) {
				String itemname = item.getName();
				Double itemprice = item.getPrice();
				ServiceItemBean serviceitem = new ServiceItemBean(itemname,
						Integer.parseInt(item_count[1]), itemprice);
				serviceitemlist.add(serviceitem);
			}
		}
		ServiceOrderBean serviceorderbean = new ServiceOrderBean(order.getId(),
				order.getAmount(), serviceitemlist, order.getSecretCode(),
				username, vendorname, order.getStatus(), order.getTimeStamp());
		return serviceorderbean;
	}

	@GET
	@Path("/mobile/list/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersListByUserId(@PathParam("userid") int userid) {
		try {
			List<ServiceOrderListBean> ServiceOrderBeanList = new ArrayList<ServiceOrderListBean>();
			
			List<Order> orders = orderFactory.getModelByUserid(userid);// Change to Userid
																		
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			for (Order order : orders) {

				Vendor vendor = vendorfactory.getModelById(order.getVendorID());
				String vendorname = vendor.getName()
						+ " - "
						+ locationfactory.getModelById(vendor.getLocationID())
								.getCode();

				ServiceOrderListBean serviceorderbean = new ServiceOrderListBean(
						order.getId(), order.getAmount(), vendorname,
						order.getTimeStamp());

				ServiceOrderBeanList.add(serviceorderbean);

			}
			GenericEntity<List<ServiceOrderListBean>> entity = new GenericEntity<List<ServiceOrderListBean>>(
					Lists.newArrayList(ServiceOrderBeanList)) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();
			
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/itemlist/{orderid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsForAnOrder(@PathParam("orderid") int orderid) {
		try {
			List<ServiceItemBean> serviceitemlist = new ArrayList<ServiceItemBean>();

			String items_counts = orderFactory.getModelById(orderid)
					.getItems_count();

			for (String eachitem : items_counts.split(",")) {

				String[] item_count = eachitem.split(":");
				Item item = itemFactory.getModelById(Integer
						.parseInt(item_count[0]));
				if (item != null) {
					String itemname = item.getName();
					Double itemprice = item.getPrice();
					ServiceItemBean serviceitem = new ServiceItemBean(itemname,
							Integer.parseInt(item_count[1]), itemprice);
					serviceitemlist.add(serviceitem);
				}
			}
			GenericEntity<List<ServiceItemBean>> entity = new GenericEntity<List<ServiceItemBean>>(
					Lists.newArrayList(serviceitemlist)) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/vendorlist/{venid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersByVendor(@PathParam("venid") int vendorid) {
		try {
			List<OrderVendorBean> OrderVendorBeanList = new ArrayList<OrderVendorBean>();
			
			List<Order> orders = orderFactory.getModelByVendor(vendorid);
			if (httpRequest.getContentType() != null) {
				type = httpRequest.getContentType();
			}

			for (Order order : orders) {

				String username = userfactory.getModelById(order.getUserid())
						.getmailid();
				OrderVendorBean orderVendorBean = new OrderVendorBean(
						order.getId(), order.getAmount(), username,
						order.getStatus(), order.getTimeStamp());
				OrderVendorBeanList.add(orderVendorBean);
			}

			GenericEntity<List<OrderVendorBean>> entity = new GenericEntity<List<OrderVendorBean>>(
					Lists.newArrayList(OrderVendorBeanList)) {
			};
			return Response.ok(entity, type).cacheControl(cc).build();

		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderFilter(@Context UriInfo queryParams) {
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

				List<Order> orders = orderFactory.getModelWithFilter(filterBy);
				GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(
						Lists.newArrayList(orders)) {
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

	@POST
	@Path("/mobile/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Postorder(String OrderPostBean) {
		JSONObject result = null;
		try {
			if ((OrderPostBean == null) || (OrderPostBean.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("OrderPostBean is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();

				OrderPostBean orderPostBean = genson.deserialize(OrderPostBean,
						OrderPostBean.class);

				if (orderPostBean.getAuth_token().equals(
						userfactory.getModelById(orderPostBean.getUserid())
								.getAuth_token())) {

					Map<Integer, List<ItemCountBean>> vendorMap = new HashMap<Integer, List<ItemCountBean>>();
					List<ItemCountBean> items_counts = orderPostBean.getItemcount();
					String Timestamp = orderPostBean.getTimeStamp();
					for (ItemCountBean itemCountbean : items_counts) {
						Item item = itemFactory.getModelById(itemCountbean
								.getItemid());
						if (item == null) {
							result = new JSONObject();
							result.put("result",
									"Payment failed.Selected Item has been removed");
							return Response.status(200).entity(result.toString())
									.type(type).cacheControl(cc).build();
						}
						if (item.getCount() <= 0
								|| item.getCount() < itemCountbean.getCount()
								|| !item.getIsActive()) {
							result = new JSONObject();
							result.put("result",
									"Payment failed." + item.getName()
											+ " is currently not available");
							return Response.status(200).entity(result.toString())
									.type(type).cacheControl(cc).build();

						}
						int vendorid = item.getVendorID();
						Vendor ven = vendorfactory.getModelById(vendorid);
						if (ven == null) {
							result = new JSONObject();
							result.put("result",
									"Payment failed. Selected Vendor has been removed");
							return Response.status(200).entity(result.toString())
									.type(type).cacheControl(cc).build();
						}
						if (!ven.getIsActive()) {
							result = new JSONObject();
							result.put("result", "Payment failed." + ven.getName()
									+ " is currently not available");
							return Response.status(200).entity(result.toString())
									.type(type).cacheControl(cc).build();
						}
						if (vendorMap.containsKey(vendorid)) {
							vendorMap.get(vendorid).add(itemCountbean);
						} else {
							List<ItemCountBean> itemlist = new ArrayList<ItemCountBean>();
							itemlist.add(itemCountbean);
							vendorMap.put(vendorid, itemlist);
						}
					}
					for (Entry<Integer, List<ItemCountBean>> vendoritemslist : vendorMap
							.entrySet()) {
						Double TotalAmount = new Double(0.00);
						StringBuilder items_count = new StringBuilder();
						List<Item> modifieditemlist = new ArrayList<Item>();
						for (ItemCountBean ItemCountbean : vendoritemslist
								.getValue()) {
							modifieditemlist.clear();
							Item item = itemFactory.getModelById(ItemCountbean
									.getItemid());
							if (item.getCount() <= 0
									|| item.getCount() < ItemCountbean
											.getCount() || !item.getIsActive()) {
								result = new JSONObject();
								result.put("result",
										"Payment failed." + item.getName()
												+ " is currently not available");
								return Response.status(200)
										.entity(result.toString()).type(type)
										.cacheControl(cc).build();

							}
							Double itemTotalprice = item.getPrice()
									* ItemCountbean.getCount();
							TotalAmount = TotalAmount + itemTotalprice;
							item.setCount(item.getCount()
									- ItemCountbean.getCount());
							items_count.append((new StringBuilder(item.getId()
									.toString()
									+ ":"
									+ ItemCountbean.getCount()).append(",")));
							modifieditemlist.add(item);
							// itemFactory.put(item);
						}
						// create order for each vendor

						Order postorder = new Order(3, TotalAmount,
								items_count.substring(0,
										items_count.length() - 1),
								Utility.randomString(8), "paid",
								orderPostBean.getUserid(), vendoritemslist.getKey(),
								Timestamp);
						User updateuser = userfactory.getModelById(orderPostBean
								.getUserid());
						Double currbal = updateuser.getAvailableBalance()
								- TotalAmount;
						updateuser.setAvailableBalance(currbal);
						//Process Order for each vendor 
						utilfactory.processOrder(updateuser, modifieditemlist,
								postorder);
					}
					result = new JSONObject();
					result.put("result", "OK");
					result.put("UserId", orderPostBean.getUserid());
					result.put("AvailBalance",
							userfactory.getModelById(orderPostBean.getUserid())
									.getAvailableBalance());
					return Response.status(201).entity(result.toString())
							.type(type).cacheControl(cc).build();
				} else {
					result = new JSONObject();
					result.put("result", "Auth-token failed");
					return Response.status(200).entity(result.toString())
							.type(type).cacheControl(cc).build();
				}

			}

			

		}catch (Exception e) {
			String json = "{\"result\":\"Payment Failed:Server Error\"}";
			return Response.status(200).entity(json.toString()).type(type)
					.cacheControl(cc).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Post(String Order) {
		try {
			if ((Order == null) || (Order.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Order is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();

				Order order = genson.deserialize(Order, Order.class);

				orderFactory.post(order);

				return Response.status(201).entity(order).type(type)
						.cacheControl(cc).build();

			}
		}catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/statusupdate/{code}")
	public Response postOrderStatus(@PathParam("code") String code) {
		try {
			if ((code == null) || (code.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("code is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				List<Order> orderList = orderFactory.getModelBySecretCode(code);
				if (orderList.size() != 0) {
					Order order = orderList.get(0);
					if (order.getStatus() != "printed") {
						order.setStatus("printed");
						orderFactory.put(order);
						return Response.status(200).entity("ok").type(type)
								.cacheControl(cc).build();
					} else {
						return Response.status(200)
								.entity("Bill already printed").type(type)
								.cacheControl(cc).build();
					}

				} else {
					return Response.status(200).entity("Invalid Code ")
							.type(type).cacheControl(cc).build();
				}

			}
		}catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@GET
	@Path("/statusupdatepaid/{code}")
	public Response Poststatuspaid(@PathParam("code") String code) {
		try {
			if ((code == null) || (code.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("code is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				List<Order> orderList = orderFactory.getModelBySecretCode(code);
				if (orderList.size() != 0) {
					Order order = orderList.get(0);

					order.setStatus("paid");
					orderFactory.put(order);
					return Response.status(200).entity("ok").type(type)
							.cacheControl(cc).build();

				} else {
					return Response.status(200).entity("Invalid Code ")
							.type(type).cacheControl(cc).build();
				}

			}
		}catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Path("/vendororderexpire")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response markAllOrdersAsExpired(int vendorid) {
		//To mark all orders as expired
	
		try {
			if ((vendorid == 0)) {
				return Response.status(400).cacheControl(cc)
						.entity("Order is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}

				orderFactory.markOrdersAsExpired(vendorid);
				return Response.status(200).entity("Updated Successfully")
						.type(type).cacheControl(cc).build();
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Path("/orderexpire")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response markOrderAsExpired(int orderid) {
		try {
			if ((orderid == 0)) {
				return Response.status(400).cacheControl(cc)
						.entity("Order is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				Order order = orderFactory.getModelById(orderid);
				order.setStatus("expired");
				orderFactory.put(order);
				return Response.status(200).entity("Updated Successfully")
						.type(type).cacheControl(cc).build();
			}
		} catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response PutLocation(String orderJson) {
		try {
			if ((orderJson == null) || (orderJson.isEmpty())) {
				return Response.status(400).cacheControl(cc)
						.entity("Order is null").build();
			} else {
				if (httpRequest.getContentType() != null) {
					type = httpRequest.getContentType();
				}
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Genson genson = new GensonBuilder().useDateFormat(format)
						.create();
				Order order = genson.deserialize(orderJson, Order.class);

				orderFactory.put(order);
				return Response.status(200).entity(order).type(type)
						.cacheControl(cc).build();
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
				Order order = orderFactory.getModelById(id);
				if (order == null)
					return Response.status(200)
							.entity("No Records Found for Deletion")
							.cacheControl(cc).build();
				else {
					orderFactory.delete(id);
					return Response.status(200).cacheControl(cc).entity("OK")
							.build();
				}
			}
		}catch (Exception e) {
			return Response.status(500).cacheControl(cc)
					.entity("Internal Server Error").build();
		}
	}
}
