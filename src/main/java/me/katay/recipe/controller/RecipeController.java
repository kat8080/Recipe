package me.katay.recipe.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import me.katay.recipe.service.impl.ReciepeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
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
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Рецепт добавлен успешно.",
            content = {@Content(mediaType = "application/json")}))
    public ResponseEntity<?> addReciepe(@RequestBody Recipe recipe) throws ReciepeException {
        if (StringUtils.isBlank(recipe.getTitle())) {
            return ResponseEntity.badRequest().body("Нет названия рецепта");
        }
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    @GetMapping("/{id}")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Рецепт найден успешно.",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Рецепт на найден.", content = {})})
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") Long id) throws ReciepeException {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Изменение добавлено успешно.",
            content = {@Content(mediaType = "application/json")}))
    public ResponseEntity<Void> updateRecipe(@PathVariable("id") Long id, @RequestBody Recipe reciepe) {
        Recipe recipe = recipeService.update(id, reciepe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Рецепт удален успешно.",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Рецепт на найден.", content = {})})
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") Long id) {
        if (recipeService.remove(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
