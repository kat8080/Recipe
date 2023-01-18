package me.katay.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;  //название
    private int quantity;  //количество
    private String measure;  //единица измерения

}
