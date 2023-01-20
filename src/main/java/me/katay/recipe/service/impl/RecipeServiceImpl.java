package me.katay.recipe.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.katay.recipe.model.Ingredient;
import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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

    private final Path pathToTxtTemplate;

    private final ObjectMapper objectMapper;

    public RecipeServiceImpl(@Value("${application.file.recipes}") String path) throws ReciepeException {
        try {
            this.path = Paths.get(path);
            this.pathToTxtTemplate = Paths.get(RecipeServiceImpl.class.getResource("recipesTemplate.txt").toURI());
            this.objectMapper = new ObjectMapper();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw e;
        } catch (URISyntaxException e) {
            e.printStackTrace();
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

    private void writeDataFromFile(Map<Long, Recipe> recipes) {
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
            writeDataFromFile(recipes);
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
    public void update(Long id, Recipe recipe) throws ReciepeException {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            writeDataFromFile(recipes);
        } else {
            throw new ReciepeException("По такому id рецепта нет.");
        }
    }

    @Override
    public boolean remove(Long id) {
        recipes.remove(id);
        writeDataFromFile(recipes);
        return true;
    }

    @Override
    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }

    @Override
    public byte[] getAllInBytes() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void importRecipes(MultipartFile recipes) {
        try {
            Map<Long, Recipe> mapFromRequest = objectMapper.readValue(recipes.getBytes(), new TypeReference<>() {
            });
            writeDataFromFile(mapFromRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] exportTxt() {
        try {
            String template = Files.readString(pathToTxtTemplate, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (Recipe recipe : recipes.values()) {
                StringBuilder ingredients = new StringBuilder();
                StringBuilder steps = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredients.append(" - ").append(ingredient).append("\n");
                }
                int stepCounter = 1;
                for (String step : recipe.getSteps()) {
                    steps.append(stepCounter++).append(". ").append(step).append("\n");
                }
                String recipeData = template.replace("%title%", recipe.getTitle())
                        .replace("%minutesCook%", String.valueOf(recipe.getMinutesCook()))
                        .replace("%ingredients%", ingredients.toString())
                        .replace("%steps%", toString());
                stringBuilder.append(recipeData).append("\n\n\n");
            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
