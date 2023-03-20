package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Pageable;

public interface RecipeService {
    Page<Recipe> findBySearchCriteria(Specification<Recipe> build, Pageable page);
    void save(Recipe recipe);
}
