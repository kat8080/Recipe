package me.katay.recipe.service;

import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.impl.ReciepeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe) throws ReciepeException;

    Recipe getRecipe(Long id) throws ReciepeException;

    void update(Long id, Recipe recipe) throws ReciepeException;

    boolean remove(Long id);

    List<Recipe> getAll();

    byte[] getAllInBytes();

    void importRecipes(MultipartFile recipes);
}
