package com.recipemanager.favouriterecipeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class Instruction {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @ApiModelProperty(required = false)
    String id;
    @Column(nullable = false)
    @ApiModelProperty(required = true)
    String value;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "recipe_id")
    Recipe recipe;
}
