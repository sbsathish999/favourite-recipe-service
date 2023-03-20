package com.recipemanager.favouriterecipeservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class APIResponse {
    List<?> data;
    HttpStatus responseCode;
    String message;

    public static APIResponse build(String message, HttpStatus responseCode, List<?> data){
        return APIResponse.builder().responseCode(responseCode).message(message).data(data).build();
    }
}
