package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.config.SearchOperation;
import com.recipemanager.favouriterecipeservice.model.*;
import javax.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class RecipeSpecification implements Specification<Recipe> {

    private SearchCriteria searchCriteria;

    public RecipeSpecification(SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root
            , CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();


        switch(Objects.requireNonNull(
                SearchOperation.getSimpleOperation
                        (searchCriteria.getOperation()))){
            case CONTAINS:
                if(searchCriteria.getFilterKey().equals("ingredients")) {
                    return cb.like(cb.lower(ingredientJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch + "%");
                } else if(searchCriteria.getFilterKey().equals("instructions")) {
                    return cb.like(cb.lower(instructionJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root
                                .get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");

            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterKey().equals("ingredients"))
                {
                    return cb.notLike(cb.lower(ingredientJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch +"%");
                } else if(searchCriteria.getFilterKey().equals("instructions")) {
                    return cb.notLike(cb.lower(instructionJoin(root).
                                    <String>get(searchCriteria.getFilterKey())),
                            "%" + strToSearch + "%");
                }
                return cb.notLike(cb.lower(root
                                .get(searchCriteria.getFilterKey())),
                        "%" + strToSearch + "%");
            case EQUAL:
                if(searchCriteria.getFilterKey().equals("servings") || searchCriteria.getFilterKey().equals("type")) {
                    return cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
                }
        }
        return null;
    }

    private Join<Recipe, Instruction> instructionJoin(Root<Recipe> root){
        return root.join("value");
    }

    private Join<Recipe, Ingredient> ingredientJoin(Root<Recipe> root){
        return root.join("recipe_id");
    }
}
