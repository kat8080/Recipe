package me.katay.recipe.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.katay.recipe.model.Ingredient;
import me.katay.recipe.service.IngredientService;
import me.katay.recipe.service.impl.IngredientException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping( )
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Ингридиент добавлен успешно.",
            content = {@Content(mediaType = "application/json")}))
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient) throws IngredientException {
        if (StringUtils.isBlank(ingredient.getName())) {
            return ResponseEntity.badRequest().body("Нужно написать название ингридиента");
        }
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }
    @GetMapping("/{id}")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ингридиент найден успешно.",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Рецепт на найден.", content = {})})
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") Long id) throws IngredientException {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Изменение добавлено успешно.",
            content = {@Content(mediaType = "application/json")}))
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.update(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ингридиент удален успешно.",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Рецепт на найден.", content = {})})
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") Long id) {
        if (ingredientService.remove(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadRecipes() {
        InputStreamResource inputStreamResource = ingredientService.getAllInBytes();
        if (inputStreamResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                //ghyujhgjkugbnhyj.contentLength(length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" + ingredients.json\"")
                .body(inputStreamResource);
    }


}
