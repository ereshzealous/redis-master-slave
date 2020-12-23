package com.spring.redis.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.redis.user.model.WSUserDetails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created on 23/December/2020 By Author Eresh, Gorantla
 **/
@RestController
@RequestMapping("/api")
public class UserController {

	private final StringRedisTemplate template;

	final ObjectMapper objectMapper;

	public UserController(StringRedisTemplate template, ObjectMapper objectMapper) {
		this.template = template;
		objectMapper.registerModule(new JavaTimeModule());
		this.objectMapper = objectMapper;
	}

	@PutMapping("/user")
	public ResponseEntity<WSUserDetails> saveUserDetails(@RequestBody WSUserDetails request) {
		if (StringUtils.isBlank(request.getId())) {
			request.setId(UUID.randomUUID().toString());
		} else {
			request = getWsUserDetails(request.getId());
		}
		if (request.getCreatedAt() == null) {
			request.setCreatedAt(LocalDateTime.now());
		}
		request.setUpdatedAt(LocalDateTime.now());
	 	String value = null;
		try {
			value = objectMapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.template.opsForHash().put(request.getId(), request.getId(), value);
		return ResponseEntity.ok(request);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<WSUserDetails> getUserDetails(@PathVariable String id) {
		WSUserDetails userDetails = getWsUserDetails(id);
		return ResponseEntity.ok(userDetails);
	}


	private WSUserDetails getWsUserDetails(String id) {
		String value  = (String) this.template.opsForHash().get(id, id);
		WSUserDetails userDetails = null;
		try {
			userDetails = this.objectMapper.readValue(value, WSUserDetails.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if (userDetails == null) {
			throw new RuntimeException("User Details Not Found for Id :: " + id);
		}
		return userDetails;
	}
}