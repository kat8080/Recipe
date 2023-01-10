package me.katay.recipe.service.impl;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long ingredientId;
    private static Map<Long, Ingredient> ingredients = new HashMap<>();

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredientId)) {
            throw new RuntimeException("Такой енгридиент уже есть.");
        } else {
            ingredients.put(ingredientId++, ingredient);
        }
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Long ingredientId) {
        if (ingredients.containsKey(ingredientId)) {
            return ingredients.get(ingredientId);
        } else {
            throw new RuntimeException("Нет такого ингридиента");
        }
    }
}
