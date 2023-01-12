package me.katay.recipe.service.impl;


import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Long recipeId = 0L;

    private static final Map<Long, Recipe> recipes = new HashMap<>();



    @Override
    public Recipe addRecipe(Recipe recipe) throws ReciepeException {
        if (recipes.containsKey(recipeId)) {
            throw new ReciepeException("Такой рецепт уже есть.");
        } else {
            recipes.put(recipeId++, recipe);
        }
        return recipe;
    }

    @Override
    public Recipe getRecipe(Long id) throws ReciepeException {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        } else {
            throw new ReciepeException("По такому id рецепта нет.");
        }
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            return recipes.put(id, recipe);
        }
        return null;
    }

    @Override
    public Recipe remove(Long id) {
        return recipes.remove(id);
    }

    @Override
    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }
}
