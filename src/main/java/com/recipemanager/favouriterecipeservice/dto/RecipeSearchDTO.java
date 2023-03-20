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
public class RecipeSearchDTO {

    List<SearchCriteria> searchCriteriaList;
    String dataOption;
}
