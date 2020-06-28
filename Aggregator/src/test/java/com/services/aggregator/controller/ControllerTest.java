
  package com.services.aggregator.controller;
  
  import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.services.aggregator.model.Customer;
import com.services.aggregator.model.Product;

  @ExtendWith(SpringExtension.class)
  @WebMvcTest(Controller.class)
 public class ControllerTest {
  

  @Autowired MockMvc mockMvc;
  
  @MockBean
  Controller controller;
	private static com.services.aggregator.model.OrderHeader order;
	private static com.services.aggregator.model.CustAddress custadd;
	private static Product pro;
	private static Customer cs;
	@BeforeAll
	public static void init() {
		custadd = new com.services.aggregator.model.CustAddress();
		custadd.setState("TN");
		custadd.setCountry("INDIA");
		custadd.setCity("Vpm");

		cs = new com.services.aggregator.model.Customer();
		cs.setAddress(custadd);
		cs.setCustName("Stella");
		cs.setCustomerID((long) 1);
		pro = new Product();
		pro.setId((long) 1);
		pro.setName("Trimmer");

		order = new com.services.aggregator.model.OrderHeader();
		order.setCustomer(cs);
		order.setProduct(pro);
		order.setQuantity(1);

	}
  
  @Test void testviewProductById() throws Exception { 
	  Mockito.when(controller.viewProductById((long) 1)).thenReturn(pro);
		mockMvc.perform(MockMvcRequestBuilders.get("/enduser/searchProductbyid/1")).andDo(print())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Trimmer"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value((long)1));
			
  }
  
 
 
	@Test
	void testViewProductsByName() throws Exception {
		List<Product> arr = new ArrayList<Product>();
		arr.add(pro);
		
		  Mockito.when(controller.viewProductsByName("Trimmer")).thenReturn(arr);
			MvcResult mvcresult=mockMvc.perform(MockMvcRequestBuilders.get("/enduser/searchProductbyname/Trimmer")).andDo(print()).andReturn();
			String expected = "[{id:1,name:Trimmer,description:null,price:null,category:null}]";

			JSONAssert.assertEquals(expected, mvcresult.getResponse().getContentAsString(), false);
			
	}

	@Test
	void testViewMyDetails() throws Exception {
		 Mockito.when(controller.viewMyDetails((long)1)).thenReturn(cs);
			mockMvc.perform(MockMvcRequestBuilders.get("/enduser/viewmydetails/1")).andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.custName").value("Stella"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.customerID").value((long)1));
	}



	@Test
	void testGetMyOrder() throws Exception {
		  Mockito.when(controller.getMyOrder(1)).thenReturn(order);
			mockMvc.perform(MockMvcRequestBuilders.get("/enduser/getmyorder/1")).andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.product.name").value("Trimmer"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.customer.custName").value("Stella"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.customer.address.state").value("TN"));
	}

	@Test
	void testUpdateMyDetails() throws Exception {
		Mockito.when(controller.updateMyDetails(((long)1),cs)).thenReturn("DetailsUpdated");

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/enduser/updatemydetails/1")
				.content(new ObjectMapper().writeValueAsString(cs)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		
		
	}

	@Test
	void testDeleteOrder()  throws Exception{
		String s = "deleted";
		Mockito.when(controller.deleteOrder(1)).thenReturn(s);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/enduser/deleteorder/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "deleted");
	}

}
