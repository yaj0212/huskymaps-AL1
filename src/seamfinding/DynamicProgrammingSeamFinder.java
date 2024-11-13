package seamfinding;

import seamfinding.energy.EnergyFunction;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        double[][] DPTable = new double[picture.width()][picture.height()];
        for (int y = 0; y < picture.height(); y++) {
            DPTable[0][y] = f.apply(picture, 0, y);
        }

        for (int x = 1; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                double min = Double.MAX_VALUE;
                for (int z = y - 1; z <= y + 1; z++) {
                    if (z >= 0 && z < picture.height()) {
                        double energy = DPTable[x - 1][z];
                        if (energy < min) {
                            min = energy;
                        }
                    }
                }
                DPTable[x][y] = f.apply(picture, x, y) + min;
            }
        }

        List<Integer> shortest_path = new ArrayList<>();
        double min = Double.MAX_VALUE;
        int miny = 0;
        for (int y = 0; y < picture.height(); y++) {
            if (DPTable[picture.width() - 1][y] < min) {
                min = DPTable[picture.width() - 1][y];
                miny = y;
            }
        }
        shortest_path.add(miny);
        for (int x = picture.width() - 1; x > 0; x--) {
            min = Double.MAX_VALUE;
            int temp = miny;
            for (int y = miny - 1; y <= miny + 1; y++) {
                if (y >= 0 && y < picture.height()) {
                    if (DPTable[x - 1][y] < min) {
                        min = DPTable[x - 1][y];
                        temp = y;
                    }
                }
            }
            miny = temp;
            shortest_path.add(miny);
        }
        Collections.reverse(shortest_path);
        return shortest_path;
    }
}
