package com.recipemanager.favouriterecipeservice.repository;

import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RecipeUser, String> {
    RecipeUser findByEmail(String email);
}
