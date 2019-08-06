package com.target.marketing.product.service;

import com.target.marketing.exception.TargetServiceException;

public interface IProductService {

	boolean postComment(String comment) throws TargetServiceException;

}
