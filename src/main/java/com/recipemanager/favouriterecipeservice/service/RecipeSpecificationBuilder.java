package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecipeSpecificationBuilder {

    private final List<SearchCriteria> params;

    public RecipeSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final RecipeSpecificationBuilder with(String key,
                                              String operation, Object value){
        params.add(new SearchCriteria(key, value, operation));
        return this;
    }

    public final RecipeSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Recipe> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Recipe> result = new RecipeSpecification(params.get(0));
        for (int index = 1; index < params.size(); index++){
            SearchCriteria criteria = params.get(index);
            result =  Specification.where(result).and(new RecipeSpecification(criteria));
        }
        return result;
    }
}