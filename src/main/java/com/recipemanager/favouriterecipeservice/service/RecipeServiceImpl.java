package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.repository.RecipeRepository;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity findBySearchCriteria(String userId, List<SearchCriteria> criteriaList) {
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder();
        try {
            if (userId == null || userId.isEmpty() || criteriaList == null || criteriaList.isEmpty()) {
                throw new RuntimeException("Invalid input");
            }
            RecipeUser user = userRepository.findById(userId).orElse(null);
            if(user == null) {
                throw new RuntimeException("User not found");
            }
            SearchCriteria userCriteria = new SearchCriteria("user", user, "eq");
            criteriaList.add(userCriteria);
            criteriaList.forEach(x -> builder.with(x));
            List<Recipe> searchResult = repository.findAll(builder.build());
            return ResponseEntity.ok(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity save(Recipe recipe) {
        try {
            if(recipe != null && recipe.getUser() != null && recipe.getUser().getId() != null) {
                RecipeUser user = null;
                try{
                    user = userRepository.findById(recipe.getUser().getId()).orElse(null);
                    if(user == null) {
                        throw new RuntimeException("user not found");
                    }
                }catch (Exception e) {
                    throw new RuntimeException("user not found");
                }
                repository.save(recipe);
            } else {
                throw new RuntimeException("Invalid input");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
