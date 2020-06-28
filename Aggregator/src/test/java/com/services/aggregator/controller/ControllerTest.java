/*
 * package com.services.aggregator.controller;
 * 
 * import static org.junit.jupiter.api.Assertions.*; import static
 * org.mockito.Mockito.when;
 * 
 * import org.junit.Before; import org.junit.jupiter.api.BeforeAll; import
 * org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.junit.runner.RunWith;
 * import org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.junit.MockitoJUnitRunner; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.web.client.RestTemplateBuilder; import
 * org.springframework.test.context.junit.jupiter.SpringExtension; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.test.util.ReflectionTestUtils; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.netflix.discovery.EurekaClient; //import
 * com.order.controller.OrderController; import
 * com.services.aggregator.model.Product;
 * 
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest class ControllerTest {
 * 
 * @InjectMocks private static Controller c;
 * 
 * @Mock private RestTemplateBuilder restTemplateBuilder;
 * 
 * @Autowired MockMvc mockMvc;
 * 
 * @Mock private EurekaClient eurekaClient;
 * 
 * @Test void test() { // fail("Not yet implemented"); }
 * 
 * @Before public static void init() { ReflectionTestUtils.setField(c,
 * "noProductExits", "no product"); }
 * 
 * @Test void testviewProductById() { RestTemplate restTemplate =
 * restTemplateBuilder.build(); Product p = new Product(); p.setId((long) 1);
 * p.setName("sanetizer"); p.setDescription("use in hands");
 * p.setCategory("sanetizer"); p.setPrice((double) 300);
 * 
 * String fetchProductbaseurl = "http://localhost:9093/" +
 * "/product/getoneProduct/" + "/" + 1;
 * when(restTemplate.getForObject(fetchProductbaseurl,
 * Product.class)).thenReturn(p); // Product p1 =
 * restTemplate.getForObject(fetchProductbaseurl, Product.class); Product p1 =
 * c.viewProductById((long) 1); assertEquals(p, p1);
 * 
 * }
 * 
 * }
 */