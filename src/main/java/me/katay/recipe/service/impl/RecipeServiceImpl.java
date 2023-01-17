package me.katay.recipe.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Long recipeId = 0L;

    private static final Map<Long, Recipe> recipes = new HashMap<>();

    private final Path path;


    private final ObjectMapper objectMapper;

    public RecipeServiceImpl(@Value("${application.file.recipes}") String path) throws ReciepeException {
        try {
            this.path = Paths.get(path);
            this.objectMapper = new ObjectMapper();
        } catch (InvalidPathException e) {
            throw new ReciepeException("Что то пошло не так.");
        }
    }

    @PostConstruct
    public void init() {
        readDataFromFile();
    }

    private void readDataFromFile() {
        try {
            byte[] file = Files.readAllBytes(path);
            Map<Long, Recipe> mapFromFile = objectMapper.readValue(file, new TypeReference<>() {
            });
            recipes.putAll(mapFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDataFromFile() {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(recipes);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Recipe addRecipe(Recipe recipe) throws ReciepeException {
        if (recipes.containsValue(recipeId)) {
            throw new ReciepeException("Такой рецепт уже есть.");
        } else {
            Recipe newRecipe = recipes.put(recipeId++, recipe);
            writeDataFromFile();
            return newRecipe;
        }
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
    public void update(Long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            writeDataFromFile();
        }
    }

    @Override
    public boolean remove(Long id) {
        recipes.remove(id);
        writeDataFromFile();
        return true;
    }

    @Override
    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }
}
