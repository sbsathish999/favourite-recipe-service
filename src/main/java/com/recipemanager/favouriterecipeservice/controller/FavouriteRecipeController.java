package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite-recipes")
public class FavouriteRecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    @PostMapping("/search/{userId}")
    public ResponseEntity searchRecipes(@PathVariable String userId, @RequestBody List<SearchCriteria> searchCriteriaList) {
        return recipeService.findBySearchCriteria(userId, searchCriteriaList);
    }
}
