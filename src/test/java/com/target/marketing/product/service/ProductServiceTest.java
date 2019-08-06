package com.target.marketing.product.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	ProductService productService;

	@Test
	public void postValidCommentTest() throws Exception {
		assertThat(productService.postComment("good"), equalTo(false));
	}

	@Test
	public void postInvalidCommentTest() throws Exception {
		assertThat(productService.postComment("bad"), equalTo(true));
	}
}
