package me.katay.recipe.service;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.impl.IngredientException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.InputStreamResource;

public interface IngredientService {


    Ingredient addIngredient(Ingredient ingredient) throws IngredientException;   //добавление ингридиента

    Ingredient getIngredient(Long id) throws IngredientException;      //получение ингридиента

    Ingredient update(Long id, Ingredient ingredient);

    boolean remove(Long id);

    InputStreamResource getAllInBytes();
}
