package com.recipemanager.favouriterecipeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class Ingredient {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @ApiModelProperty(required = false)
    String id;
    @Column(nullable = false)
    @ApiModelProperty(required = true)
    String ingredients;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    Recipe recipe;
}
