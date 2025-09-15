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
 * Comparator for sorting Recipe objects by total time taken (in minutes).
 * Sorts in ascending order, with quicker recipes appearing first.
 */
public class TimeTakenComparator implements Comparator<Recipe> {
    /**
     * Compares two recipes based on the total time taken to prepare and cook.
     *
     * @param o1 the first recipe
     * @param o2 the second recipe
     * @return a negative integer if o1 takes less time than o2,
     *         a positive integer if o1 takes more time than o2,
     *         or 0 if they take the same amount of time
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Integer.compare(o1.getTimeTaken(), o2.getTimeTaken());
    }
}
