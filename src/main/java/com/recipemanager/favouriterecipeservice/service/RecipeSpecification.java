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
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();
        switch(Objects.requireNonNull(
                SearchOperation.getSimpleOperation
                        (searchCriteria.getOperation()))){
            case CONTAINS:
                if(searchCriteria.getFilterKey().equals("ingredients")) {
                    Join<Recipe, Ingredient> join = ingredientJoin(query, root);
                    Expression<String> ex = join.
                            <String>get(searchCriteria.getFilterKey());
                    return cb.like(cb.lower(ex),"%" + strToSearch + "%");
                } else if(searchCriteria.getFilterKey().equals("instructions")) {
                    Join<Recipe, Instruction> join = instructionJoin(query, root);
                    Expression<String> ex = join.
                            <String>get(searchCriteria.getFilterKey());
                    return cb.like(cb.lower(ex),"%" + strToSearch + "%");
                }

                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())),"%" + strToSearch + "%");
            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterKey().equals("ingredients"))
                {
                    Join<Recipe, Ingredient> join = ingredientJoin(query, root);
                    Expression<String> ex = join.
                            <String>get(searchCriteria.getFilterKey());
                    return cb.and(cb.notLike(cb.lower(ex),"%" + strToSearch + "%"));
                } else if(searchCriteria.getFilterKey().equals("instructions")) {
                    Join<Recipe, Instruction> join = instructionJoin(query, root);
                    Expression<String> ex = join.
                            <String>get(searchCriteria.getFilterKey());
                    return cb.and(cb.notLike(cb.lower(ex),"%" + strToSearch + "%"));
                }
                return cb.and(cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())),"%" + strToSearch + "%"));
            case EQUAL:
                if(searchCriteria.getFilterKey().equals("servings") || searchCriteria.getFilterKey().equals("type")) {
                    return cb.and(cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue()));
                }
        }
        return null;
    }

    private Join<Recipe, Instruction> instructionJoin(CriteriaQuery<?> query, Root<Recipe> root){
        query.multiselect(
                root.get("id"));
        query.groupBy(root.get("id"));
        return root.join("instructions", JoinType.LEFT);
    }

    private Join<Recipe, Ingredient> ingredientJoin(CriteriaQuery<?> query, Root<Recipe> root){
        query.multiselect(
                root.get("id"));
        query.groupBy(root.get("id"));
        return root.join("ingredients", JoinType.LEFT);
    }
}
