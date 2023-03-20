package com.recipemanager.favouriterecipeservice.model;

import javax.persistence.*;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class Recipe {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @ApiModelProperty(required = false)
    String id;
    @Column(nullable = false)
    @ApiModelProperty(required = true)
    String name;
    @Column(nullable = false)
    @ApiModelProperty(required = true)
    String type;
    @Column(nullable = false)
    @ApiModelProperty(required = true)
    Integer servings;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    List<Ingredient> ingredients;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    List<Instruction> instructions;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @ApiModelProperty(required = true)
    RecipeUser user;
}
