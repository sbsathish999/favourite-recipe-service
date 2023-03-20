package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import org.springframework.http.ResponseEntity;

public interface RecipeUserService {
    ResponseEntity save(RecipeUser user);
    ResponseEntity get(String userId, String email);
}
