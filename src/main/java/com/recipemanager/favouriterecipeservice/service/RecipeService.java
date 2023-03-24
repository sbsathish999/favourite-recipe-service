package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;

import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {
    ResponseEntity findBySearchCriteria(String userId, List<SearchCriteria> searchCriteriaList);
    ResponseEntity save(Recipe recipe);
}
