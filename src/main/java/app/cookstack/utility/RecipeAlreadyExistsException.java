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

package app.cookstack.utility;

/**
 * Exception thrown when attempting to add a recipe that already exists
 * in the recipe collection (e.g., duplicate recipe name).
 */
public class RecipeAlreadyExistsException extends Exception {

    /**
     * Constructs a new RecipeAlreadyExistsException with no detail message.
     */
    public RecipeAlreadyExistsException() {}

    /**
     * Constructs a new RecipeAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public RecipeAlreadyExistsException(String message) {
        super(message);
    }
}

