package com.target.marketing.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.marketing.common.TargetConstant;
import com.target.marketing.product.service.IProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	private static final String URL_MAPPING = "/product/1/comment";

	MockMvc mockMvc;

	@InjectMocks
	ProductController productController;

	@Mock
	IProductService productService;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void postValidCommentTest() throws Exception {
		when(productService.postComment(anyString())).thenReturn(false);

		this.mockMvc
				.perform(post(URL_MAPPING).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(getPayload("good"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.feedback", Matchers.is("Comment is accepted")));
		verify(productService, times(1)).postComment(anyString());

	}

	@Test
	public void postInvalidCommentTest() throws Exception {
		when(productService.postComment(anyString())).thenReturn(true);

		this.mockMvc
				.perform(post(URL_MAPPING).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(getPayload("bad"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.feedback", Matchers.is("Comment is not accepted")));
		verify(productService, times(1)).postComment(anyString());

	}

	private Map<String, String> getPayload(String comment) {
		Map<String, String> payload = new HashMap<>();
		payload.put(TargetConstant.COMMENT, comment);
		return payload;
	}
}
