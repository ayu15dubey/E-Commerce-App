package com.order.serviceLayer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.order.entity.CustAddress;
import com.order.entity.OrderHeader;

@Service
public interface ServiceLayer {
	
	String orderPlaced(OrderHeader orderHead);

	String orderUpdated(CustAddress address, int orderId);

	OrderHeader orderFetched(int orderId);

	List<OrderHeader> allOrderFetched();

	String orderRemoved(int orderId);
}
