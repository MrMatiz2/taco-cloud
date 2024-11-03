package com.example.tacocloud.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class IngredientRef {

    @Id
    private Long id;
    private String ingredient;
    private Long taco;

}
