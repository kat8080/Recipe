package me.katay.recipe.service;

import me.katay.recipe.model.Ingredient;

public interface IngredientService {


    Ingredient addIngredient(Ingredient ingredient);   //добавление ингридиента

    Ingredient getIngredient(Long ingredientId);      //получение ингридиента
}
