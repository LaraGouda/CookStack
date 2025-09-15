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
 * Comparator for sorting Recipe objects alphabetically by name.
 * Sorting is done in ascending (A–Z) order using lexicographical comparison.
 */
public class NameComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on their names.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return a negative integer if o1's name comes before o2's,
     *         a positive integer if it comes after,
     *         or 0 if they are equal
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return o1.getRecipeName().compareTo(o2.getRecipeName());
    }
}