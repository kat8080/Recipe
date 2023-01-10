package me.katay.recipe.controller;

import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/add")
    public Recipe addReciepe(@RequestBody Recipe recipe) {
        return recipe;
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipe(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getRecipe(recipeId);
    }
}
