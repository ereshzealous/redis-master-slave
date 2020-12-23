package com.spring.redis.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created on 23/December/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserDetails {
	private String id;
	private String name;
	private String city;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}