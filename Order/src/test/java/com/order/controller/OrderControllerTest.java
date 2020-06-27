package com.order.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.entity.CustAddress;
import com.order.entity.Customer;
import com.order.entity.OrderHeader;
import com.order.entity.Product;
import com.order.repo.OrderRepo;
import com.order.serviceLayer.ServiceLayer;


@ExtendWith(SpringExtension.class)
@WebMvcTest	(OrderController.class)
public class OrderControllerTest {

	
		@Autowired
		MockMvc mockMvc;
	@MockBean
	ServiceLayer serviceLayer;
	@MockBean
     OrderRepo orderRepo;


	private static OrderHeader order;
	private static CustAddress custadd;
	private static Product pro;
	private static Customer cs;
	@BeforeAll
	public static void init() {
	custadd=new CustAddress();
		  custadd.setState("TN");
		  custadd.setCountry("INDIA");
		  custadd.setCity("Vpm");
		  
		   cs=new Customer();
		  cs.setAddress(custadd);
		  cs.setCustName("Stella");
		  cs.setCustomerID((long) 1)
		  ;
		 pro=new Product();
		  pro.setId((long) 1);
		  pro.setName("Trimmer");
		  
		 order =new OrderHeader();
		  order.setCustomer(cs);
		  order.setProduct(pro);
		  order.setQuantity(1);
		  
			
	}
	
	@Test
	public void testOrderPlaced() throws JsonProcessingException, Exception {
		 MockHttpServletRequest request = new MockHttpServletRequest();
	        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
System.out.println(order.getCustomer().getCustName());

	      Mockito.when(serviceLayer.orderPlaced((OrderHeader)org.mockito.Matchers.any(OrderHeader.class))).thenReturn("orderPlaced");
	         
	   MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/placeOrder").content(new ObjectMapper().writeValueAsString(order)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print()).andReturn();
	      int status = result.getResponse().getStatus();
	      assertEquals(201, status);
	      String content = result.getResponse().getContentAsString();
	      assertEquals(content, "orderPlaced");
		
	}

	@Test
	public void testOrderUpdated() throws Exception  {
	      Mockito.when(serviceLayer.orderUpdated(any(CustAddress.class), anyInt())).thenReturn("orderUpdated");
	         
	      MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.put("/updateOrder/1/").content(new ObjectMapper().writeValueAsString(custadd)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	     assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "orderUpdated");
	}

	@Test
	public void testOrderFetched() throws Exception {
		
		Mockito.when(serviceLayer.orderFetched(anyInt())).thenReturn(order);
		mockMvc.perform(MockMvcRequestBuilders.get("/fetchOrder/1")).andExpect(status().isOk()).andDo(print())
		.andExpect(MockMvcResultMatchers.jsonPath("$.product.name").value("Trimmer"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath("$.customer.custName").value("Stella"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.customer.address.state").value("TN"));
	}

	@Test
	public void testAllOrderFetched() throws Exception  {
		List<OrderHeader> arr=new ArrayList<OrderHeader>();
		arr.add(order);
		Mockito.when(serviceLayer.allOrderFetched()).thenReturn(arr);
		MvcResult mvcresult=mockMvc.perform(MockMvcRequestBuilders.get("/fetchAllOrder").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn();
String expected= "[{orderId:0,product:{id:1,name:Trimmer,description:null,price:null,category:null},orderDate:null,customer:{customerID:1,custName:Stella,phoneNumber:null,email:null,address:{city:Vpm,state:TN,country:INDIA,pinCode:null}},orderStatus:null,quantity:1,totalPrice:0}]";
	
JSONAssert.assertEquals(expected,mvcresult.getResponse().getContentAsString(),false);
	Mockito.verify(serviceLayer).allOrderFetched();
	}

	@Test
	public void testOrderRemoved() throws Exception {
		String s="orderRemoved";
		Mockito.when(serviceLayer.orderRemoved(anyInt())).thenReturn(s);
	      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/removeOrder/1")).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "orderRemoved");
	}

}



