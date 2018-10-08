package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.exception.ProductNotFoundException;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

public class ProductServiceTest {
@Mock
ProductRepository productRepository;
MockMvc mockMvc;
@InjectMocks
ProductServiceImpl productServiceImpl;

@Before
public void setUp() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(productServiceImpl).build();

}
@Test
public void testAddProductWhichReturnsProduct() {
	Product product=new Product(1235,"cell","shipp",1200);
	when(productRepository.save(product)).thenReturn(product);
	assertEquals(product, productServiceImpl.addProduct(product));
}
@Test
public void testUpdateProductWhichReturnsProduct() {
	Product product=new Product(1235,"Apple","shipp",1200);
	when(productRepository.save(product)).thenReturn(product);
	assertEquals(product,productServiceImpl.updateProduct(product));
	
}
@Test
public void testDeleteProductWhichReturnProduct() {
	Product product=new Product(1235,"Apple","shipp",1200);
	productServiceImpl.deleteProduct(product);
	verify(productRepository,times(1)).delete(product);
	
}
@Test
public void testFindProductByIdWhichReturnsProduct() throws ProductNotFoundException {
	Product product=new Product(1235,"Apple","shipp",1200);
	when(productRepository.findById(1235)).thenReturn(Optional.of(product));
	assertEquals(product, productServiceImpl.findProductById(1235));
	
}
}
