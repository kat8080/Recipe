package me.katay.recipe.controller;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.IngredientService;
import me.katay.recipe.service.impl.IngredientException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping( )
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) throws IngredientException {
        return ingredientService.addIngredient(ingredient);
    }
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable("id") Long id) throws IngredientException {
        return ingredientService.getIngredient(id);
    }

    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public Ingredient deleteIngredient(@PathVariable("id") Long id) {
        return ingredientService.remove(id);
    }
}
