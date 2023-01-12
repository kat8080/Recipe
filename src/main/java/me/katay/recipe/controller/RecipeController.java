package me.katay.recipe.controller;

import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import me.katay.recipe.service.impl.ReciepeException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    private List<Recipe> getAll() {
        return recipeService.getAll();
    }
    @PostMapping()
    public Recipe addReciepe(@RequestBody Recipe recipe) throws ReciepeException {
        return recipeService.addRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable("id") Long id) throws ReciepeException {
        return recipeService.getRecipe(id);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable("id") Long id, @RequestBody Recipe reciepe) {
        return recipeService.update(id, reciepe);
    }

    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable("id") Long id) {
        return recipeService.remove(id);
    }
}
