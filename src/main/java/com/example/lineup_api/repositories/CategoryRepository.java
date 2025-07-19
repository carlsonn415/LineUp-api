package com.example.lineup_api.repositories;

import com.example.lineup_api.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}