package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.APIResponse;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RecipeUserServiceImpl implements RecipeUserService{

    @Autowired
    UserRepository repository;
    @Override
    public ResponseEntity save(RecipeUser user) {
        try{
            repository.save(user);
        }catch (Exception e) {
            ResponseEntity.unprocessableEntity().body(APIResponse.build(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, Arrays.asList(user)));
        }
        return ResponseEntity.ok(APIResponse.build(null, HttpStatus.OK, Arrays.asList(user)));
    }

    @Override
    public ResponseEntity get(String userId, String email) {
        RecipeUser user = null;
        try{
            if(userId != null && !userId.isEmpty()){
                user = repository.findById(userId).orElse(null);
            } if(email != null && !email.isEmpty()) {
                user = repository.findByEmail(email);
            }
            if(user == null) {
                throw new RuntimeException("User not found");
            }
            return ResponseEntity.ok(user);

        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
