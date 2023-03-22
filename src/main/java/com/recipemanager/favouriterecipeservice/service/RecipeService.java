package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.RecipeSearchDTO;
import com.recipemanager.favouriterecipeservice.model.Recipe;

import org.springframework.http.ResponseEntity;

public interface RecipeService {
    ResponseEntity findBySearchCriteria(Integer pageNum, Integer pageSize, RecipeSearchDTO recipeSearchDTO);
    ResponseEntity save(Recipe recipe);
}
