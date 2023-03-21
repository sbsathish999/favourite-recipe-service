package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.dto.RecipeSearchDTO;
import com.recipemanager.favouriterecipeservice.dto.RecipeSearchV1DTO;
import com.recipemanager.favouriterecipeservice.model.APIResponse;
import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.service.RecipeService;
import com.recipemanager.favouriterecipeservice.service.RecipeSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/favourite-recipes")
public class FavouriteRecipeController {

    @Autowired
    RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity<APIResponse> save(@RequestBody Recipe recipe) {
        try {
            recipeService.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(APIResponse.build(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, null));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<APIResponse> searchRecipes(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum
                                                    , @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
                                                    , @RequestBody RecipeSearchDTO recipeSearchDTO) {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();
        List<SearchCriteria> criteriaList = recipeSearchDTO.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x ->
            {
                x.setDataOption(recipeSearchDTO.getDataOption());
                builder.with(x);
            });
        }

        Pageable page = PageRequest.of(pageNum, pageSize
                                , Sort.by("name").ascending());

        Page<Recipe> employeePage = recipeService.findBySearchCriteria(builder.build(), page);


        return ResponseEntity.ok(APIResponse.build(null, HttpStatus.OK, employeePage.toList()));
    }
    @PostMapping("/search/v1")
    public ResponseEntity<APIResponse> searchRecipesV1(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum
            , @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
            , @RequestBody RecipeSearchV1DTO recipeSearchDTO) {

        Pageable page = PageRequest.of(pageNum, pageSize
                , Sort.by("name").ascending());

        List<Recipe> employeePage = recipeService.findBySearchCriteriaV1(recipeSearchDTO);


        return ResponseEntity.ok(APIResponse.build(null, HttpStatus.OK, employeePage));
    }
}
