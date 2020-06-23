package com.order.serviceLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.order.entity.CustAddress;
import com.order.entity.Customer;
import com.order.entity.OrderHeader;
import com.order.repo.OrderRepo;

@Service
public class ServiceLayerImpl implements ServiceLayer {

	@Value("${order.orderplaced}")
	String orderPlaced;

	@Value("${order.status}")
	String orderStatus;
	
	@Value("${order.quantity}")
	String quantityLeft;
	
	@Value("${order.updated}")
	String orderUpdated;
	
	@Value("${order.removed}")
	String orderRemoved;
	
	@Value("${order.wrongid}")
	String wrongId;

	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private EurekaClient eurekaclient;
	@Autowired
	private RestTemplateBuilder restbuild;

	public String orderPlaced(OrderHeader orderHead) {

		RestTemplate resttem = restbuild.build();

		InstanceInfo insinfoInv = eurekaclient.getNextServerFromEureka("inventory", false);
		String baseurlInv = insinfoInv.getHomePageUrl();
		String fetchQuantitybaseurl = baseurlInv + "/quantityFetch/" + orderHead.getProduct().getId();
		String quantity = resttem.getForObject(fetchQuantitybaseurl, String.class);

		if (Integer.parseInt(quantity) < orderHead.getQuantity()) {
			return quantityLeft;
		}

		String updateQuantitybaseurl = baseurlInv + "/updateQuantity/" + orderHead.getProduct().getId() + "/"
				+ orderHead.getQuantity();
		resttem.put(updateQuantitybaseurl, null);

		Date date = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String dat = DATE_FORMAT.format(date);
		orderHead.setOrderDate(dat);
		orderHead.setOrderStatus(orderStatus);

		int totalPrice = orderHead.getTotalPrice() * orderHead.getQuantity();
		orderHead.setTotalPrice(totalPrice);

		orderRepo.save(orderHead);
		return orderPlaced;

	}

	public String orderUpdated(CustAddress address, int orderId) {

		OrderHeader orderHeader = orderRepo.findById(orderId).get();
		Customer customer = orderHeader.getCustomer();
		// CustAddress address = customer.getAddress();

		customer.setAddress(address);
		;
		orderHeader.setCustomer(customer);

		orderRepo.save(orderHeader);
		return orderUpdated;
	}

	public OrderHeader orderFetched(int orderId) {
		if(orderRepo.existsById(orderId))
		return orderRepo.findById(orderId).get();
		
		else
			return null;
	}

	public List<OrderHeader> allOrderFetched() {
		return orderRepo.findAll();
	}

	public String orderRemoved(int orderId) {

		if (orderRepo.existsById(orderId)) {
			orderRepo.deleteById(orderId);
			return orderRemoved;
		} else {
			return wrongId;
		}
	}
}
