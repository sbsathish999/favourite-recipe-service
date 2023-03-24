package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeUserServiceImpl implements RecipeUserService{

    @Autowired
    UserRepository repository;
    @Override
    public ResponseEntity save(RecipeUser user) {
        try{
            if(user == null || user.getEmail() == null || user.getName() == null) {
                throw new RuntimeException("Invalid input");
            }
            user =  repository.save(user);
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity get(String userId, String email) {
        RecipeUser user = null;
        try{
            if(userId != null && !userId.isEmpty()){
                user = repository.findById(userId).orElse(null);
            } else if(email != null && !email.isEmpty()) {
                user = repository.findByEmail(email);
            } else {
                throw new RuntimeException("Invalid input");
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
