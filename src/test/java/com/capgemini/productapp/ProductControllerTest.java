package com.capgemini.productapp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

public class ProductControllerTest {
		
	@InjectMocks
	ProductController productController;
	@Mock
	ProductService productService;
	MockMvc mockMvc;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		 mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	@Test
	public void testAddProductWhichReturnsProduct() throws Exception {
		Product product=new Product(1235,"cell","shipp",1200);	
	when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
	mockMvc.perform(post("/product")
			 .contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\r\n" + 
					"	\"productId\":1235,\r\n" + 
					"	\"productName\":\"cell\",\r\n" + 
					"\"productCategory\":\"shipp\",\r\n" + 
					"	\"productPrice\":1200\r\n" + 
					"}")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId").value(1235))
			.andExpect(jsonPath("$.productName").value("cell"))
			.andExpect(jsonPath("$.productCategory").value("shipp"))
			.andExpect(jsonPath("$.productPrice").value(1200))
			.andDo(print());

  }
	@Test
	public void testFindProductByIdWhichReturnsProduct()throws Exception{
		Product product=new Product(1235,"cell","shipp",1200);	
	when(productService.findProductById(1235)).thenReturn(product);
		mockMvc.perform(get("/products/1235")				
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.productId").value(1235))
					.andExpect(jsonPath("$.productName").value("cell"))
					.andExpect(jsonPath("$.productCategory").value("shipp"))
					.andExpect(jsonPath("$.productPrice").value(1200))
					.andDo(print());

		  }
	@Test
	public void testUpdateProductWhichReturnsProduct() throws Exception {
		Product product=new Product(1235,"Apple","shipp",1200);	
		when(productService.findProductById(1235)).thenReturn(product);
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(product);
		mockMvc.perform(put("/product")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"	\"productId\":1235,\r\n" + 
						"	\"productName\":\"Apple\",\r\n" + 
						"\"productCategory\":\"shipp\",\r\n" + 
						"	\"productPrice\":1200\r\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value(1235))
				.andExpect(jsonPath("$.productName").value("Apple"))
				.andExpect(jsonPath("$.productCategory").value("shipp"))
				.andExpect(jsonPath("$.productPrice").value(1200))
				.andDo(print());
		
		verify(productService).findProductById(product.getProductId());
	}
	@Test
	public void testDeleteProduct()throws Exception{
		Product product=new Product(1235,"Apple","shipp",1200);	
		when(productService.findProductById(1235)).thenReturn(product);
		mockMvc.perform(delete("/products/1235").contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk());
	}
}


