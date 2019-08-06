package com.target.marketing.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.marketing.common.TargetAPIResponse;
import com.target.marketing.common.TargetConstant;
import com.target.marketing.common.TargetResponse;
import com.target.marketing.exception.TargetServiceException;
import com.target.marketing.product.service.IProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	private IProductService productService;

	@Autowired
	public ProductController(IProductService productService) {
		this.productService = productService;
	}

	@ApiOperation(nickname = "postComment", value = "This API enables user to post review/comment for specific product")
	@PostMapping(value = "/{productId}/comment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TargetAPIResponse> postComment(
			@PathVariable(value = "productId", required = true) int productId,
			@RequestBody(required = true) Map<String, String> comment, HttpServletResponse httpServletResponse)
			throws TargetServiceException {

		LOGGER.debug("postComment() >> Received request for Product ID : {}", productId);
		TargetAPIResponse targetApiResponse = new TargetAPIResponse();

		if (!comment.containsKey(TargetConstant.COMMENT)) {

			LOGGER.error("postComment() >> Exception : {}", TargetResponse.COMMENT_NOT_FOUND.getResponseMessage());
			throw new TargetServiceException(TargetResponse.COMMENT_NOT_FOUND.getResponseCode(),
					TargetResponse.COMMENT_NOT_FOUND.getResponseMessage());

		} else if (StringUtils.isBlank(comment.get(TargetConstant.COMMENT))) {

			LOGGER.error("postComment() >> Exception : {}", TargetResponse.BLANK_COMMENT.getResponseMessage());
			throw new TargetServiceException(TargetResponse.BLANK_COMMENT.getResponseCode(),
					TargetResponse.BLANK_COMMENT.getResponseMessage());

		}

		targetApiResponse.setMessageAndCode(TargetResponse.SUCCESS.getResponseMessage(),
				TargetResponse.SUCCESS.getResponseCode());

		if (productService.postComment(comment.get(TargetConstant.COMMENT))) {
			LOGGER.debug("postComment() >> Invalid comment");
			targetApiResponse.setFeedback(TargetConstant.INVALID_COMMENT);
		} else {
			LOGGER.debug("postComment() >> Valid comment");
			targetApiResponse.setFeedback(TargetConstant.VALID_COMMENT);
		}

		return new ResponseEntity<>(targetApiResponse, HttpStatus.OK);
	}
}
