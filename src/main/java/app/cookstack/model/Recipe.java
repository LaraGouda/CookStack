package app.cookstack.model;

import java.util.ArrayList;
/**
 * CookStack Project
 *
 * Author: Lara Gouda
 * Created: 2025
 * Version: 1.0
 *
 * Credits:
 * - JavaFX framework
 * - Icons & Images: Freepik, Retropi
 *
 * All rights reserved.
 */

import java.io.Serializable;
import java.util.List;

/**
 * The {@code Recipe} class represents a cooking recipe that contains information such as
 * ingredients, steps, preparation time, serving size, category, and nutritional details.
 *
 * <p>Each recipe has a name, a list of {@link Ingredient} objects, a list of step instructions,
 * the time it takes to make (in minutes), how many people it serves, its food category, and optional
 * nutrition details such as calories and protein content.
 */
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;
    private String recipeName;
    private List<Ingredient> recipeIngredients;
    private List<String> recipeSteps;
    private int timeTaken; // minutes
    private int servingSize;
    private String recipeCategory;
    private int recipeCalories; // in calories
    private int recipeProtein; // in grams

    /**
     * Constructs a new {@code Recipe} with full details, including calories and protein.
     *
     * @param name         the name of the recipe
     * @param ingredients  the list of ingredients used
     * @param steps        the steps required to make the recipe
     * @param time         the total time taken to prepare the recipe (in minutes)
     * @param serving      the number of servings
     * @param category     the category (e.g., Breakfast, Dessert, etc.)
     * @param calories     the number of calories
     * @param protein      the amount of protein in grams
     */
    public Recipe(String name, List<Ingredient> ingredients, List<String> steps, int time, int serving, String category, int calories, int protein) {
        this.recipeName = name;
        this.recipeIngredients = ingredients;
        this.recipeSteps = steps;
        this.timeTaken = time;
        this.servingSize = serving;
        this.recipeCategory = category;
        this.recipeCalories = calories;
        this.recipeProtein = protein;
    }

    /**
     * Constructs a {@code Recipe} without nutritional information (calories and protein).
     *
     * @param recipeName       the name of the recipe
     * @param recipeIngredients the list of ingredients
     * @param recipeSteps      the list of preparation steps
     * @param timeTaken        time to prepare in minutes
     * @param servingSize      number of servings
     * @param recipeCategory   the recipe's food category
     */
    public Recipe(String recipeName, List<Ingredient> recipeIngredients, List<String> recipeSteps, int timeTaken, int servingSize, String recipeCategory) {
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.timeTaken = timeTaken;
        this.servingSize = servingSize;
        this.recipeCategory = recipeCategory;
        this.recipeCalories = 0;
        this.recipeProtein = 0;
    }

    /**
     * Constructs a {@code Recipe} without nutritional information (calories and protein).
     *
     * @param recipeName       the name of the recipe
     * @param newRecipeIngredients the list of ingredients
     * @param steps      the list of preparation steps
     */
    public Recipe(String recipeName, String newRecipeIngredients, List<String> steps, int time, int serving, String category, int calories, int protein) {
        this.recipeName = recipeName;
        recipeIngredients = new ArrayList<>();
        Ingredient newIngredient = new Ingredient(newRecipeIngredients);
        recipeIngredients.add(newIngredient);
        recipeSteps = steps;
        timeTaken = time;
        servingSize = serving;
        recipeCategory = category;
        recipeCalories = calories;
        recipeProtein = protein;
    }

    /**
     * Constructs a {@code Recipe} without nutritional information (calories and protein).
     *
     * @param recipeName       the name of the recipe
     * @param newRecipeIngredients the list of ingredients
     * @param newRecipeSteps      the list of preparation steps
     */
    public Recipe(String recipeName, String newRecipeIngredients, String newRecipeSteps) {
        this.recipeName = recipeName;
        recipeIngredients = new ArrayList<>();
        recipeSteps = new ArrayList<>();
        recipeSteps.add(newRecipeSteps);
        Ingredient newIngredient = new Ingredient(newRecipeIngredients);
        recipeIngredients.add(newIngredient);
    }

    /**
     * Adds a new ingredient to the recipe.
     *
     * @param newIngredient the ingredient to add
     * @return true if added successfully
     */
    public boolean addIngredient(Ingredient newIngredient) {
        return (this.recipeIngredients.add(newIngredient));
    }

    /**
     * Adds a new preparation step to the recipe.
     *
     * @param step the step to add
     * @return true if added successfully
     */
    public boolean addStep(String step) {
        return (this.recipeSteps.add(step));
    }

    /**
     * Removes a specific ingredient from the recipe.
     *
     * @param removedIngredient the ingredient to remove
     * @return true if removed successfully
     */
    public boolean removeIngredient(Ingredient removedIngredient) {
        return (this.recipeIngredients.remove(removedIngredient));
    }

    /**
     * Removes a specific step from the recipe.
     *
     * @param stepRemove the step to remove
     * @return true if removed successfully
     */
    public boolean removeStep(String stepRemove) {
        return (this.recipeSteps.remove(stepRemove));
    }

    /**
     * Removes an ingredient at a specific index.
     *
     * @param index the index of the ingredient to remove
     * @return the removed {@code Ingredient} object
     */
    public Ingredient removeIngredientIndex(int index) {
        return this.recipeIngredients.remove(index);
    }

    /**
     * Removes a preparation step at a specific index.
     *
     * @param index the index of the step to remove
     * @return the removed step as a {@code String}
     */
    public String removeStepIndex(int index) {
        return this.recipeSteps.remove(index);
    }

    /** @return the name of the recipe */
    public String getRecipeName() {
        return recipeName;
    }

    /** @param name the new name of the recipe */
    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    /** @return the list of ingredients */
    public List<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    /** @param ingredients the new list of ingredients */
    public void setRecipeIngredients(List<Ingredient> ingredients) {
        this.recipeIngredients = ingredients;
    }

    /** @return the list of recipe steps */
    public List<String> getRecipeSteps() {
        return recipeSteps;
    }

    /** @param steps the new list of recipe steps */
    public void setRecipeSteps(List<String> steps) {
        this.recipeSteps = steps;
    }

    /** @return the time taken to prepare the recipe (in minutes) */
    public int getTimeTaken() {
        return timeTaken;
    }

    /** @param time the new time taken (in minutes) */
    public void setTimeTaken(int time) {
        this.timeTaken = time;
    }

    /** @return the number of servings */
    public int getServingSize() {
        return servingSize;
    }

    /** @param serving the new serving size */
    public void setServingSize(int serving) {
        this.servingSize = serving;
    }

    /** @return the recipe category */
    public String getRecipeCategory() {
        return recipeCategory;
    }

    /** @param category the new category of the recipe */
    public void setRecipeCategory(String category) {
        this.recipeCategory = category;
    }

    /** @return the total calories of the recipe */
    public int getRecipeCalories() {
        return recipeCalories;
    }

    /** @param calories the new calorie value */
    public void setRecipeCalories(int calories) {
        this.recipeCalories = calories;
    }

    /** @return the total protein (in grams) of the recipe */
    public int getRecipeProtein() {
        return recipeProtein;
    }

    /** @param protein the new protein value (in grams) */
    public void setRecipeProtein(int protein) {
        this.recipeProtein = protein;
    }

    /**
     * Returns a formatted string representation of the recipe.
     *
     * @return a detailed string with ingredients, steps, and nutritional info
     */
    public String toString() {
        String s;
        if (recipeCalories == 0) {
            s = ((recipeName) + ": \n" + "Ingredients: " + (recipeIngredients.toString()) + "\n" + "Steps: " + (recipeSteps.toString()) + "\n" + "Time Taken: " + (timeTaken) + " minutes \n" + "Serving Size: " + (servingSize) + " people\n" + "Recipe Category: " + (recipeCategory) + "\n" + "Calories: null \n Protein: null");
        }
        else {
            s = ((recipeName) + ": \n" + "Ingredients: " + (recipeIngredients.toString()) + "\n" + "Steps: " + (recipeSteps.toString()) + "\n" + "Time Taken: " + (timeTaken) + " minutes \n" + "Serving Size: " + (servingSize) + " people\n" + "Recipe Category: " + (recipeCategory) + "\n" + "Calories: " + (recipeCalories) + " calories \n" + "Protein: " + (recipeProtein) + " grams");
        }
        return s;
    }

}

