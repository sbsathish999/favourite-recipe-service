package com.recipemanager.favouriterecipeservice.UnitTestCases.service;

import com.recipemanager.favouriterecipeservice.model.Recipe;
import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.model.SearchCriteria;
import com.recipemanager.favouriterecipeservice.repository.RecipeRepository;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import com.recipemanager.favouriterecipeservice.service.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    UserRepository userRepository;

    @Mock
    RecipeRepository repository;

    @Test
    public void testSave_CheckForExceptionMessageWhenRecipeIsNull(){
        ResponseEntity entity = recipeService.save(null);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(entity.getBody().toString().equals("Invalid input"));
    }

    @Test
    public void testSave_CheckForExceptionMessageWhenUserIsNull(){
        ResponseEntity entity = recipeService.save(mock(Recipe.class));
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(entity.getBody().toString().equals("Invalid input"));
    }

    @Test
    public void testSave_CheckForExceptionMessageWhenUserIDIsNull(){
        Recipe recipe = new Recipe();
        RecipeUser user = new RecipeUser();
        recipe.setUser(user);
        ResponseEntity entity = recipeService.save(recipe);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(entity.getBody().toString().equals("Invalid input"));
    }

    @Test
    public void testSave_CheckForExceptionMessageWhenInvalidUserIDIsPassed(){
        Recipe recipe = new Recipe();
        RecipeUser user = new RecipeUser();
        user.setId("invalid");
        recipe.setUser(user);
        ResponseEntity entity = recipeService.save(recipe);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(entity.getBody().toString().equals("user not found"));
    }

    @Test
    public void testSave_CheckForExceptionMessageWhenFindByUserIDReturnsNull(){
        Recipe recipe = new Recipe();
        RecipeUser user = new RecipeUser();
        user.setId("deleteduserid");
        recipe.setUser(user);
        when(userRepository.findById("deleteduserid")).thenReturn(null);
        ResponseEntity entity = recipeService.save(recipe);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(entity.getBody().toString().equals("user not found"));
    }

    @Test
    public void testSave_CheckForSuccessResponseWhenRecipeSaved(){
        Recipe recipe = new Recipe();
        RecipeUser user = new RecipeUser();
        user.setId("valid-user-id");
        recipe.setUser(user);
        when(userRepository.findById("valid-user-id")).thenReturn(Optional.of(user));
        when(repository.save(recipe)).thenReturn(recipe);
        ResponseEntity entity = recipeService.save(recipe);
        assertTrue(entity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void testFindBySearchCriteria_CheckFor422IfUserIsNull() {
       ResponseEntity result = recipeService.findBySearchCriteria("", null);
       assertTrue(result.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testFindBySearchCriteria_CheckFor422IfSearchCrieteriaListIsNull() {
        ResponseEntity result = recipeService.findBySearchCriteria("userId", null);
        assertTrue(result.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testFindBySearchCriteria_CheckFor422IfSearchCrieteriaListIsEmpty() {
        ResponseEntity result = recipeService.findBySearchCriteria("userId", new ArrayList<>());
        assertTrue(result.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
    }
    @Test
    public void testFindBySearchCriteria_CheckFor422IfUserIsEmpty() {
        ResponseEntity result = recipeService.findBySearchCriteria(null, null);
        assertTrue(result.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
    }
    @Test
    public void testFindBySearchCriteria_CheckFor422IfUserIsNotFound() {
        SearchCriteria searchCriteria = mock(SearchCriteria.class);
        when(userRepository.findById("invaliduserId")).thenReturn(null);
        ResponseEntity result = recipeService.findBySearchCriteria("invaliduserId", Arrays.asList(searchCriteria));
        assertTrue(result.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testFindBySearchCriteria_CheckForEmptyRecipeListIfSearchCriteriaNotMatching() {
        SearchCriteria searchCriteria = mock(SearchCriteria.class);
        RecipeUser user = mock(RecipeUser.class);
        when(userRepository.findById("userId")).thenReturn(Optional.ofNullable(user));
        when(repository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());
        List<Recipe> result = (List<Recipe>) recipeService.findBySearchCriteria("userId", new ArrayList<>(Arrays.asList(searchCriteria))).getBody();
        assertTrue(result.isEmpty());
    }
}

