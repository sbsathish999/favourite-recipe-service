package com.recipemanager.favouriterecipeservice.dto;

import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeSearchV1DTO {

    List<String> includeIngredients;
    List<String> excludeIngredient;
    String instructionSearchText;
    Boolean isVeg;
    Integer servings;
}
