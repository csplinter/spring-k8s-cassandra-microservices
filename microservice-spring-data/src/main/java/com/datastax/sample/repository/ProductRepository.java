package com.datastax.sample.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.datastax.sample.entity.Product;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends CassandraRepository<Product, String> {
    
    /**
     * Illustration of custom queries
     */
    @Query("select * from products where name = ?0")
    Optional<Product> findByProductName(String name);
    
}

