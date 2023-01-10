package me.katay.recipe.service.impl;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Long recipeId;
    private static List<Ingredient> ingredients = new ArrayList<>();
    private static List<String> steps = new ArrayList<>();
    private static Map<Long, Recipe> recipes = new HashMap<>();


    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsKey(recipeId)) {
            throw new RuntimeException("Такой рецепт уже есть.");
        } else {
            recipes.put(recipeId++, recipe);
        }
        return recipe;
    }

    @Override
    public Recipe getRecipe(Long recipeId) {
        if (recipes.containsKey(recipeId)) {
            return recipes.get(recipeId);
        } else {
            throw new RuntimeException("По такому id рецепта нет.");
        }
    }
}
