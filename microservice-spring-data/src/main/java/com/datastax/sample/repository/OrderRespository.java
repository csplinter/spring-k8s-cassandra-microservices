package com.datastax.sample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.datastax.sample.entity.Order;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "orders", description = "Spring Data Rest")
@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRespository extends CrudRepository<Order, String> {}
