package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.RecipeSearchDTO;
import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.repository.RecipeRepository;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository repository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity findBySearchCriteria(Integer pageNum, Integer pageSize, RecipeSearchDTO recipeSearchDTO) {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();
        List<SearchCriteria> criteriaList = recipeSearchDTO.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x ->
            {
                x.setDataOption(recipeSearchDTO.getDataOption());
                builder.with(x);
            });
        }

        Pageable page = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        Page<Recipe> searchResult = repository.findAll(builder.build(), page);
        return ResponseEntity.ok(searchResult);
    }

    @Override
    public ResponseEntity save(Recipe recipe) {
        try {
            if(recipe.getUser() != null && recipe.getUser().getId() != null) {
                RecipeUser user = null;
                try{
                    user = userRepository.findById(recipe.getUser().getId()).get();
                    if(user == null) {
                        throw new RuntimeException("User not found");
                    }
                }catch (Exception e) {
                    throw new RuntimeException("user not found");
                }
                repository.save(recipe);
            } else {
                throw new RuntimeException("Invalid input");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
