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
 * Exception thrown when a requested recipe cannot be found
 * in the recipe collection or database.
 */
public class RecipeNotFoundException extends Exception {

    /**
     * Constructs a new RecipeNotFoundException with no detail message.
     */
    public RecipeNotFoundException() {}

    /**
     * Constructs a new RecipeNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public RecipeNotFoundException(String message) {
        super(message);
    }
}



