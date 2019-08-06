package com.target.marketing.product.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.target.marketing.exception.TargetServiceException;

@Service
public class ProductService implements IProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Override
	public boolean postComment(String comment) throws TargetServiceException {

		return getObjectionableContent().stream().filter(e -> !e.isEmpty())
				.anyMatch(e -> comment.toLowerCase().contains(e.toLowerCase()));

	}

	private static List<String> getObjectionableContent() throws TargetServiceException {

		List<String> objectionableContentList = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new ClassPathResource("/objectionable_content.txt").getInputStream()));) {

			bufferedReader.lines().filter(line -> !line.isEmpty()).forEach(line -> {
				objectionableContentList.add(line);
			});

		} catch (IOException ioException) {
			LOGGER.error("getObjectionableContent() >> Exception : {}", ioException.getMessage());
			throw new TargetServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ioException.getMessage());
		}

		return objectionableContentList;
	}
}
