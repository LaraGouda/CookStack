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

import java.io.Serializable;

/**
 * The {@code Ingredient} class represents a single ingredient used in a recipe.
 * It includes information such as the ingredient's name,
 * quantity, and the unit of measurement.
 * This class is a basic data model used in the recipe system.
 */
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ingredientName;
    private String ingredientQuantity;
    private String measurementUnit;

    /**
     * Constructs a new {@code Ingredient} object with the specified values.
     *
     * @param name         the name of the ingredient (e.g., "Tomato")
     * @param quantity     the quantity required (e.g., "2", "1/2")
     * @param measurements the unit of measurement (e.g., "pieces", "tsp")
     */
    public Ingredient(String name, String quantity, String measurements) {
        this.ingredientName = name;
        this.ingredientQuantity = quantity;
        this.measurementUnit = measurements;
    }

    /**
     * Constructs a new {@code Ingredient} object with the specified values.
     *
     * @param name         the name of the ingredient (e.g., "Tomato")
     */
    public Ingredient(String name) {
        this.ingredientName = name;
    }

    /**
     * Returns the name of the ingredient.
     *
     * @return the ingredient name
     */
    public String getName() {
        return this.ingredientName;
    }

    /**
     * Updates the name of the ingredient.
     *
     * @param newIngredientName the new ingredient name
     */
    public void setName(String newIngredientName) {
        this.ingredientName = newIngredientName;
    }

    /**
     * Returns the quantity of the ingredient.
     *
     * @return the ingredient quantity
     */
    public String getQuantity() {
        return this.ingredientQuantity;
    }

    /**
     * Updates the quantity of the ingredient.
     *
     * @param newIngredientQuantity the new quantity
     */
    public void setQuantity(String newIngredientQuantity) {
        this.ingredientQuantity = newIngredientQuantity;
    }

    /**
     * Returns the unit of measurement used for the quantity (e.g., "cups", "grams").
     *
     * @return the measurement unit
     */
    public String getMeasurementUnit() {
        return this.measurementUnit;
    }

    /**
     * Updates the measurement unit for the ingredient quantity.
     *
     * @param newMeasurementUnit the new unit of measurement
     */
    public void setMeasurementUnit(String newMeasurementUnit) {
        this.measurementUnit = newMeasurementUnit;
    }

    /**
     * Returns a formatted string representation of the ingredient.
     * Format: "Type: Name (Quantity Unit)"
     *
     * @return a human-readable string representing the ingredient
     */
    @Override
    public String toString() {
        String s = (ingredientName) + " (" + (ingredientQuantity) + " " + (measurementUnit) + ")";
        return s;
    }
}
