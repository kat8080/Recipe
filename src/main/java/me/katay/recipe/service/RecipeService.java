package me.katay.recipe.service;

import me.katay.recipe.model.Recipe;

import java.util.Collection;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(Long recipeId);
}
