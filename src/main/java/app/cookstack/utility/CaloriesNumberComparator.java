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
 * Comparator for sorting Recipe objects by their calorie count in ascending order.
 */
public class CaloriesNumberComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on their calories.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return negative if o1 has fewer calories, positive if more, zero if equal
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Integer.compare(o1.getRecipeCalories(), o2.getRecipeCalories());
    }
}