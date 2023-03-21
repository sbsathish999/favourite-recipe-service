package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.dto.RecipeSearchV1DTO;
import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.repository.RecipeRepository;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public List<Recipe> findBySearchCriteriaV1(RecipeSearchV1DTO recipeSearchDTO) {
        String selectQuery = "SELECT r.* from recipe r ";
        String ingJoin = "Inner JOIN ingredient ing on r.id = ing.recipe_id ";
        String ing_ins_Joins = ingJoin + "Inner JOIN instruction ins on r.id = ins.recipe_id ";
        String whereCondition = "WHERE ";
        if(recipeSearchDTO.getIsVeg() != null) {
            if(recipeSearchDTO.getIsVeg()) {
                whereCondition += "r.type = 'vegetarian' and ";
            } else {
                whereCondition += "r.type <> 'vegetarian' and ";
            }
        }

        if(recipeSearchDTO.getServings() != null) {
            whereCondition += "r.servings = " + recipeSearchDTO.getServings() + " and ";
        }
        if(recipeSearchDTO.getIsVeg() != null) {
            whereCondition += "r.type = 'vegetarian' and ";
        }
        if(recipeSearchDTO.getInstructionSearchText() != null
                && !recipeSearchDTO.getInstructionSearchText().isEmpty()) {
            whereCondition += "ins.instructions like '%"
                                + recipeSearchDTO.getInstructionSearchText().toLowerCase() + "%' and ";
        }
        if(recipeSearchDTO.getIncludeIngredients() != null
                && !recipeSearchDTO.getIncludeIngredients().isEmpty()) {
           for(String ing : recipeSearchDTO.getIncludeIngredients()) {
               whereCondition += "ing.ingredients like %" + ing + "% and ";
           }
        }

        if(recipeSearchDTO.getExcludeIngredient() != null
                && !recipeSearchDTO.getExcludeIngredient().isEmpty()) {
            for(String ing : recipeSearchDTO.getExcludeIngredient()) {
                whereCondition += "ing.ingredients not like '%" + ing + "%' and ";
            }
        }
        String query = selectQuery + ing_ins_Joins;

        if(!whereCondition.endsWith("WHERE ")) {
            query += whereCondition;
            if(query.endsWith("and ")) {
                query = query.substring(0, query.length() - 4);
            }
        }
        if(recipeSearchDTO.getExcludeIngredient() != null
                && !recipeSearchDTO.getExcludeIngredient().isEmpty()) {
            String subQuery = "and r.id not in ( select r.id from recipe r " + ingJoin + " ";
            String subWhereCondition = "WHERE ";
            for(String ing : recipeSearchDTO.getExcludeIngredient()) {
                subWhereCondition += "ing.ingredients like '%" + ing + "%' and ";
            }
            subWhereCondition = subWhereCondition.substring(0, subWhereCondition.length() - 4);
            subQuery += subWhereCondition + ") ";
            query = query + subQuery;
        }
        query += "group by r.id";
        System.out.println("query : " + query);
        List<Recipe> recipes = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Recipe.class));
        System.out.println("recipes : " + recipes);
        return recipes;
    }

}
