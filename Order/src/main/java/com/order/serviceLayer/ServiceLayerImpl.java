package com.order.serviceLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.order.entity.CustAddress;
import com.order.entity.Customer;
import com.order.entity.OrderHeader;
import com.order.entity.Product;
import com.order.repo.OrderRepo;

@Service
public class ServiceLayerImpl implements ServiceLayer {

	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private EurekaClient eurekaclient;
	@Autowired
	private RestTemplateBuilder restbuild;

	public String orderPlaced(OrderHeader orderHead) {

		RestTemplate resttem = restbuild.build();
		InstanceInfo insinfo = eurekaclient.getNextServerFromEureka("product-service", false);
		String baseurl = insinfo.getHomePageUrl();

		String fetchProductbaseurl = baseurl + "/product/getoneProduct/" + "/" + orderHead.getProduct().getId();
		Product product = resttem.getForObject(fetchProductbaseurl, Product.class);
		orderHead.setProduct(product);

		InstanceInfo insinfoCust = eurekaclient.getNextServerFromEureka("customer-service", false);
		String baseurlCust = insinfoCust.getHomePageUrl();
		String fetchCustomerbaseurl = baseurlCust + "/customer/getonecutomer/" + "/"
				+ orderHead.getCustomer().getCustomerID();
		Customer customer = resttem.getForObject(fetchCustomerbaseurl, Customer.class);
		orderHead.setCustomer(customer);

		InstanceInfo insinfoInv = eurekaclient.getNextServerFromEureka("inventory-service", false);
		String baseurlInv = insinfoInv.getHomePageUrl();
		String fetchQuantitybaseurl = baseurlInv + "/quantityFetch/" + orderHead.getProduct().getId();
		String quantity = resttem.getForObject(fetchQuantitybaseurl, String.class);

		if (Integer.parseInt(quantity) < orderHead.getQuantity()) {
			return "Insufficient Stock-Only " + quantity + " left";
		}

		String updateQuantitybaseurl = baseurlInv + "/updateQuantity/" + orderHead.getProduct().getId() + "/"
				+ orderHead.getQuantity();
		resttem.put(updateQuantitybaseurl, null);

		Date date = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String dat = DATE_FORMAT.format(date);
		orderHead.setOrderDate(dat);
		orderHead.setOrderStatus("Order Recieved");

		int totalPrice = orderHead.getTotalPrice() * orderHead.getQuantity();
		orderHead.setTotalPrice(totalPrice);

		orderRepo.save(orderHead);
		return "Your Order has been Placed";

	}

	public String orderUpdated(CustAddress address, int orderId) {

		OrderHeader orderHeader = orderRepo.findById(orderId).get();
		Customer customer = orderHeader.getCustomer();
		// CustAddress address = customer.getAddress();

		customer.setAddress(address);
		;
		orderHeader.setCustomer(customer);

		orderRepo.save(orderHeader);
		return "Updated";
	}

	public OrderHeader orderFetched(int orderId) {
		return orderRepo.findById(orderId).get();
	}

	public List<OrderHeader> allOrderFetched() {
		return orderRepo.findAll();
	}

	public String orderRemoved(int orderId) {

		if(orderRepo.existsById(orderId)) {
		orderRepo.deleteById(orderId);
		return "Order Removed";
		}
		else {
			return "Wrong order ID";
		}
	}
}
