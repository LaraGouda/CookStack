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

package app.cookstack.model;

import app.cookstack.utility.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code RecipeBook} class manages a collection of {@link Recipe} objects.
 * It provides functionality to add, remove, retrieve, and sort recipes.
 * Recipes are stored both in a list (to preserve order) and a map (for fast lookup).
 */
public class RecipeBook implements Serializable {
    private static final long serialVersionUID = 1L;
    /** The User's Name. */
    private String userName;

    /** A list of all recipes in the book. Used for iteration and ordering. */
    private List<Recipe> recipeList;

    /** A map of recipe names (lowercased) to Recipe objects for quick lookup. */
    private HashMap<String, Recipe> recipeMap;

    /**
     * Constructs an empty {@code RecipeBook}.
     */
    public RecipeBook() {
        this.userName = "";
        this.recipeList = new ArrayList<Recipe>();
        this.recipeMap = new HashMap<>();
    }

    /**
     * Constructs an empty {@code RecipeBook} with a name.
     */
    public RecipeBook(String name) {
        this.userName = name;
        this.recipeList = new ArrayList<Recipe>();
        this.recipeMap = new HashMap<>();
    }

    /**
     * Adds a new recipe to the recipe book.
     *
     * @param newRecipe the recipe to add
     * @return true if the recipe was added successfully
     * @throws RecipeAlreadyExistsException if a recipe with the same name already exists
     */
    public boolean addRecipe(Recipe newRecipe) throws RecipeAlreadyExistsException {
        if (recipeMap.containsKey(newRecipe.getRecipeName().toLowerCase())) {
            throw new RecipeAlreadyExistsException("This recipe name already exists, please try again");
        }

        recipeList.add(newRecipe);
        recipeMap.put(newRecipe.getRecipeName().toLowerCase(), newRecipe);
        return true;
    }

    /**
     * Removes a recipe at the given index from the recipe list.
     *
     * @param index the index of the recipe to remove
     * @throws RecipeNotFoundException if the index is invalid
     */
    public void removeRecipe(int index) throws RecipeNotFoundException {
        if (index >= recipeList.size() || index < 0) {
            throw new RecipeNotFoundException("This index is out of reach, please try again.");
        } else {
            Recipe removed = recipeList.remove(index);
            recipeMap.remove(removed.getRecipeName().toLowerCase());
        }
    }

    /**
     * Returns the user's Name
     *
     * @return the user's Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the list of recipes.
     *
     * @return the list of recipes
     */
    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    /**
     * Returns the map of recipe names to recipe objects.
     *
     * @return the map of recipes
     */
    public HashMap<String, Recipe> getRecipeMap() {
        return recipeMap;
    }

    public Recipe getRecipeByName(String name) {
        return recipeMap.get(name);
    }

    /**
     * Sets the list of recipes. Replaces the existing list.
     *
     * @param recipes the new list of recipes
     */
    public void setRecipeList(List<Recipe> recipes) {
        this.recipeList = recipes;
    }

    /**
     * Sets the recipe map. Replaces the existing map.
     *
     * @param recipes the new recipe map
     */
    public void setRecipeMap(HashMap<String, Recipe> recipes) {
        this.recipeMap = recipes;
    }

    /**
     * Sets the user's name. Replaces the existing name.
     *
     * @param newName the new user's Name
     */
    public void setUserName(String newName) {
        this.userName = newName;
    }

    /**
     * Sorts the recipe list by time taken in ascending order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByTimeTaken() {
        this.recipeList.sort(new TimeTakenComparator());
        return recipeList;
    }

    /**
     * Sorts the recipe list by recipe name in alphabetical order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByRecipeName() {
        this.recipeList.sort(new NameComparator());
        return recipeList;
    }

    /**
     * Sorts the recipe list by number of ingredients in ascending order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByIngredientNumber() {
        this.recipeList.sort(new IngredientNumberComparator());
        return recipeList;
    }

    /**
     * Sorts the recipe list by number of steps in ascending order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByStepNumber() {
        this.recipeList.sort(new StepsTakenComparator());
        return recipeList;
    }

    /**
     * Sorts the recipe list by calorie count in ascending order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByCaloriesNumber() {
        this.recipeList.sort(new CaloriesNumberComparator());
        return recipeList;
    }

    /**
     * Sorts the recipe list by protein content in descending order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> sortByProteinNumber() {
        this.recipeList.sort(new ProteinNumberComparator());
        Collections.reverse(recipeList);
        return recipeList;
    }

    /**
     * Returns a string of all recipe names, each on a new line.
     *
     * @return a string of recipe names
     */
    public String allRecipeNames() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < recipeList.size(); i++) {
            s.append("\n").append(recipeList.get(i).getRecipeName());
        }
        return s.toString();
    }

    public void saveToFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

    public static RecipeBook loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (RecipeBook) ois.readObject();
        }
    }

    /**
     * Returns a string representation of the User's name in a title.
     *
     * @return a string of {User}'s Cookbook;
     */
    public String userCookBook() {
        return (userName + "'s CookBook");
    }

    /**
     * Returns a string representation of all recipes in the recipe book.
     *
     * @return a string listing all recipes and their details
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.userCookBook());
        for (int i = 0; i < recipeList.size(); i++) {
            s.append("\n").append(recipeList.get(i).toString());
            s.append("\n");
        }
        return s.toString();
    }

}
