package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.dto.RecipeSearchV1DTO;
import com.recipemanager.favouriterecipeservice.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
    Page<Recipe> findBySearchCriteria(Specification<Recipe> build, Pageable page);
    void save(Recipe recipe);

    List<Recipe> findBySearchCriteriaV1(RecipeSearchV1DTO recipeSearchDTO);
}
