package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FavouriteRecipeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testAddRecipe() {
        RecipeUser user = new RecipeUser("555", "Ragu", "ragu@gmail.com");
        ResponseEntity<String> saveUSer = this.restTemplate
                .postForEntity("http://localhost:" + port + "/user/save", user, String.class);
        assertEquals(200, saveUSer.getStatusCodeValue());
        RecipeUser result = this.restTemplate
                .getForObject("http://localhost:" + port + "/user/get?email=ragu@gmail.com", RecipeUser.class);
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredients("ing1");
        ingredient.setRecipe(recipe);
        Instruction instruction = new Instruction();
        instruction.setRecipe(recipe);
        instruction.setInstructions("ins1");
        recipe.setInstructions(Arrays.asList(instruction));
        recipe.setIngredients(Arrays.asList(ingredient));
        recipe.setUser(result);
        recipe.setServings(2);
        recipe.setName("testRecipe");
        recipe.setType("veg");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/favourite-recipes/save", recipe, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void testSearchRecipe()
    {
        RecipeUser user = this.restTemplate
                .getForObject("http://localhost:" + port + "/user/get?email=ragu@gmail.com", RecipeUser.class);
        List<SearchCriteria> searchCriterias = new ArrayList<>();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setValue("ins1");
        searchCriteria.setOperation("cn");
        searchCriteria.setFilterKey("instructions");
        searchCriterias.add(searchCriteria);
        List result = this.restTemplate
                .postForObject("http://localhost:" + port + "/favourite-recipes/search/" + user.getId(), searchCriterias, List.class);
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).toString().contains("testRecipe"));
    }

    @Test
    @Order(3)
    public void testGetAllRecipes() {
        List all = this.restTemplate
                .getForObject("http://localhost:" + port + "/favourite-recipes/all", List.class);
        assertTrue(all.size() == 1);
    }
}
