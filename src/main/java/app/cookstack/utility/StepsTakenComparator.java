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
 * Comparator for sorting Recipe objects based on the number of steps in their instructions.
 * Sorts in ascending order (recipes with fewer steps come first).
 */
public class StepsTakenComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on how many steps they contain.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return a negative integer if o1 has fewer steps than o2,
     *         a positive integer if o1 has more steps than o2,
     *         or 0 if they have the same number of steps
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Integer.compare((o1.getRecipeSteps()).size(), (o2.getRecipeSteps().size()));
    }
}
