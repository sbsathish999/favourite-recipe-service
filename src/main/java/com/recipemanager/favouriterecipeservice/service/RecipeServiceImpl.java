package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.repository.RecipeRepository;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository repository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Recipe> findBySearchCriteria(Specification<Recipe> spec, Pageable page) {
        Page<Recipe> searchResult = repository.findAll(spec, page);
        return searchResult;
    }

    @Override
    public void save(Recipe recipe) {
        if(recipe.getUser() != null
                && recipe.getUser().getId() != null) {
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
    }

}
