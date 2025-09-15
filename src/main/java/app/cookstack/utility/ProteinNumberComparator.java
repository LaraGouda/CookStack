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
 * Comparator for sorting Recipe objects by their protein content.
 * Sorts in ascending order based on the number of grams of protein.
 */
public class ProteinNumberComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on their protein content.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return a negative integer if o1 has less protein than o2,
     *         a positive integer if o1 has more protein than o2,
     *         or 0 if they are equal
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Integer.compare(o1.getRecipeProtein(), o2.getRecipeProtein());
    }
}

