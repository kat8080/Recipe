package me.katay.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String title;      //название
    private int minutesCook;   //сколько минут готовить
    private List<Ingredient> ingredients;   //список ингридиентов
    private List<String> steps;      //шаги приготовления


}
