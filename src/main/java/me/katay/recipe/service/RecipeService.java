package me.katay.recipe.service;

import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.impl.ReciepeException;

import java.util.List;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe) throws ReciepeException;

    Recipe getRecipe(Long id) throws ReciepeException;

    Recipe update(Long id, Recipe recipe);

    Recipe remove(Long id);

    List<Recipe> getAll();
}
