package me.katay.recipe.service.impl;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long ingredientId;
    private static final Map<Long, Ingredient> ingredients = new HashMap<>();

    @Override
    public Ingredient addIngredient(Ingredient ingredient) throws IngredientException {
        if (ingredients.containsKey(ingredientId)) {
            throw new IngredientException("Такой енгридиент уже есть.");
        }
        else {
            ingredients.put(ingredientId++, ingredient);
        return ingredient;
        }
    }

    @Override
    public Ingredient getIngredient(Long id) throws IngredientException {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new IngredientException("Нет такого ингридиента");
        }
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean remove(Long id) {
        ingredients.remove(id);
        return true;
    }
}
