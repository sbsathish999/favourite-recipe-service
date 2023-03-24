package com.recipemanager.favouriterecipeservice.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.service.RecipeSpecificationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecificationBuilderTest {

    private RecipeSpecificationBuilder recipeSpecificationBuilder;

    @BeforeEach
    void setUp() {
        recipeSpecificationBuilder = new RecipeSpecificationBuilder();
    }

    @Test
    public void shouldBuildEmptySpecification() {
        Specification<Recipe> specification = recipeSpecificationBuilder.build();
        Assertions.assertNull(specification);
    }

    @Test
    public void shouldBuildSingleSpecification() {
        recipeSpecificationBuilder.with("name", ":", "Pasta Carbonara");
        Specification<Recipe> specification = recipeSpecificationBuilder.build();
        Assertions.assertNotNull(specification);
    }

    @Test
    public void shouldBuildMultipleSpecifications() {
        recipeSpecificationBuilder
                .with("name", ":", "Pasta Carbonara")
                .with("type", ":", "Italian")
                .with("servings", ">", 2);

        Specification<Recipe> specification = recipeSpecificationBuilder.build();
        Assertions.assertNotNull(specification);
    }

    @Test
    public void shouldBuildSpecificationWithSearchCriteria() {
        SearchCriteria searchCriteria = new SearchCriteria("name", ":", "Pasta Carbonara");
        recipeSpecificationBuilder.with(searchCriteria);

        Specification<Recipe> specification = recipeSpecificationBuilder.build();
        Assertions.assertNotNull(specification);
    }
}
