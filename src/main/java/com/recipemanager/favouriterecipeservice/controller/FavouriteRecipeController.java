package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.model.RecipeSearchDTO;
import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite-recipes")
public class FavouriteRecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    @PostMapping("/search")
    public ResponseEntity searchRecipes(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum
                                                    , @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
                                                    , @RequestBody RecipeSearchDTO recipeSearchDTO) {
        return recipeService.findBySearchCriteria(pageNum, pageSize, recipeSearchDTO);
    }
}
