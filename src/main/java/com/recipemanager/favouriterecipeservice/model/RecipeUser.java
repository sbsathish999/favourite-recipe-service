package com.recipemanager.favouriterecipeservice.model;


import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class RecipeUser implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @ApiModelProperty(required = false)
    String id;

    @Column(nullable = false)
    @ApiModelProperty(required = true)
    String name;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(required = true)
    String email;
}
