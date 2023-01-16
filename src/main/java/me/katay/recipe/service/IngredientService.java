package me.katay.recipe.service;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.impl.IngredientException;

public interface IngredientService {


    Ingredient addIngredient(Ingredient ingredient) throws IngredientException;   //добавление ингридиента

    Ingredient getIngredient(Long id) throws IngredientException;      //получение ингридиента

    Ingredient update(Long id, Ingredient ingredient);

    boolean remove(Long id);
}
