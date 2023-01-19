package me.katay.recipe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.katay.recipe.model.Ingredient;
import me.katay.recipe.model.Recipe;
import me.katay.recipe.service.IngredientService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static long ingredientId;
    private static final Map<Long, Ingredient> ingredients = new HashMap<>();

    private final Path path;
    private final ObjectMapper objectMapper;


    public IngredientServiceImpl(@Value("${application.file.ingredients}") String path) {
        try {
            this.path = Paths.get(path);
            this.objectMapper = new ObjectMapper();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostConstruct
    public void init() {
        readDataFromFile();
    }

    private void readDataFromFile() {
        try {
            byte[] file = Files.readAllBytes(path);
            Map<Long, Ingredient> mapFromFile = objectMapper.readValue(file, new TypeReference<>() {
            });
            ingredients.putAll(mapFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDataFromFile() {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(ingredients);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) throws IngredientException {
        if (ingredients.containsValue(ingredient)) {
            throw new IngredientException("Такой енгридиент уже есть.");
        } else {
            Ingredient newIngredient = ingredients.put(ingredientId++, ingredient);
            writeDataFromFile();
            return newIngredient;
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
            Ingredient newIngredient = ingredients.put(id, ingredient);
            writeDataFromFile();
            return newIngredient;
        }
        return null;
    }

    @Override
    public boolean remove(Long id) {
        ingredients.remove(id);
        writeDataFromFile();
        return true;
    }

    @Override
    public InputStreamResource getAllInBytes() {
        try {
            return new InputStreamResource(new FileInputStream(path.toFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
