package com.recipemanager.favouriterecipeservice.UnitTestCases.service;

import com.recipemanager.favouriterecipeservice.model.RecipeUser;
import com.recipemanager.favouriterecipeservice.repository.UserRepository;
import com.recipemanager.favouriterecipeservice.service.RecipeUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeUserServiceImplTest {

    @InjectMocks
    RecipeUserServiceImpl userService;

    @Mock
    UserRepository repository;

    @Test
    public void testSave_CheckFor422ErrorCodeWhenUserISNull() {
        ResponseEntity entity = userService.save(null);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testSave_CheckFor422ErrorCodeWhenUserEmailNull() {
        ResponseEntity entity = userService.save(mock(RecipeUser.class));
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testSave_CheckFor422ErrorCodeWhenUserNameNull() {
        RecipeUser user = new RecipeUser();
        user.setEmail("testemail.com");
        ResponseEntity entity = userService.save(user);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testSave_CheckFor422ErrorCodeWhenSavingFailed() {
        RecipeUser user = new RecipeUser();
        user.setEmail("testemail.com");
        user.setName("test");
        when(repository.save(user)).thenThrow(new RuntimeException("Internal Server error"));
        ResponseEntity entity = userService.save(user);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGet_CheckFor422WhenUserIdAndEmailIsPassedNull() {
        ResponseEntity entity = userService.get(null, null);
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGet_CheckFor422WhenUserIdAndEmailIsPassedEmpty() {
        ResponseEntity entity = userService.get("", "");
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGet_CheckFor422WhenInvalidUserIdAPassed() {
        when(repository.findById("invalid")).thenReturn(null);
        ResponseEntity entity = userService.get("invalid", "email@gmail.com");
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Test
    public void testGet_CheckFor422WhenInvalidEmailPassed() {
        when(repository.findByEmail("invalidemail@gmail.com")).thenReturn(null);
        ResponseEntity entity = userService.get("", "invalidemail@gmail.com");
        assertTrue(entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGet_CheckFor200WhenUserRetrieved() {
        when(repository.findByEmail("abc@gmail.com")).thenReturn(mock(RecipeUser.class));
        ResponseEntity entity = userService.get(null, "abc@gmail.com");
        assertTrue(entity.getStatusCode() == HttpStatus.OK);
    }
}
