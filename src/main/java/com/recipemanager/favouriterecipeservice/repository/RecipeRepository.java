package com.recipemanager.favouriterecipeservice.repository;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String>, JpaSpecificationExecutor<Recipe> {
    Page<Recipe> findByServings(Integer servings, Pageable page);

//    Page<Recipe> findAll(String query, Pageable page);
//    Page<Recipe> findAll(String query, Pageable page);
}
