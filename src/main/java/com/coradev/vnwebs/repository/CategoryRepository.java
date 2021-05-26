package com.coradev.vnwebs.repository;

import com.coradev.vnwebs.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("select c from  Category c")
    List<Category> findTop(Pageable pageable);
}
