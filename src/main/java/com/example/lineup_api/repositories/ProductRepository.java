package com.example.lineup_api.repositories;

import com.example.lineup_api.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "category") // this cuts additional category queries
    List<Product> findByCategoryId(Byte categoryId, Sort sortBy);

    @Query("SELECT p FROM Product p JOIN FETCH p.category") // this cuts out the additional category queries as well
    List<Product> findAllWithCategory(Sort sortBy);
}