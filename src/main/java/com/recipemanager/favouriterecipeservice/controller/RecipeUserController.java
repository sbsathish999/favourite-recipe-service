package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.model.APIResponse;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.service.RecipeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class RecipeUserController {

    @Autowired
    RecipeUserService userService;

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody RecipeUser user) {
        return userService.save(user);
    }

    @GetMapping(value = "/get")
    public ResponseEntity findUser(@RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String email) {
        return userService.get(userId, email);
    }
}

