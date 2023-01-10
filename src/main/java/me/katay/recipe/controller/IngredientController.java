package me.katay.recipe.controller;

import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/add")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }
    @GetMapping("/{ingredientId}")
    public Ingredient addIngredient(@PathVariable("ingredientId") Long ingredientId) {
        return ingredientService.getIngredient(ingredientId);
    }
}
