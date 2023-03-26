package com.recipemanager.favouriterecipeservice.controller;

import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testAddUser() {
        RecipeUser user = new RecipeUser("222", "Gupta", "gupta@gmail.com");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/user/save", user, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        user = new RecipeUser("111", "Raj", "raj@gmail.com");
        responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/user/save", user, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void testGetUserByEmail()
    {
         RecipeUser result = this.restTemplate
                        .getForObject("http://localhost:" + port + "/user/get?email=gupta@gmail.com", RecipeUser.class);
        assertTrue(result.getName().equals("Gupta"));
    }
}
