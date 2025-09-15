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
import app.cookstack.model.*;
import java.util.Comparator;

/**
 * Comparator for sorting Recipe objects by the number of ingredients.
 * Sorts in ascending order based on the ingredient list size.
 */
public class IngredientNumberComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on how many ingredients they have.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return negative if o1 has fewer ingredients, positive if more, zero if equal
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Integer.compare((o1.getRecipeIngredients()).size(), (o2.getRecipeIngredients()).size());
    }
}

