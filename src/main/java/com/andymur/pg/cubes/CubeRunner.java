package com.andymur.pg.cubes;

import com.andymur.pg.cubes.data.FirstSet;
import com.andymur.pg.cubes.data.FourthSet;
import com.andymur.pg.cubes.data.SecondSet;
import com.andymur.pg.cubes.data.ThirdSet;
import com.andymur.pg.cubes.domain.facet.Facet;

import java.io.IOException;
import java.util.*;

/**
 * @author andymur
 */
public final class CubeRunner {
    public static void main(String[] args) throws IOException {
        Map<String, List<Facet>> keysToSet = keyToSet();
        Map<String, List<Facet>> inputs = new HashMap<>();

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        boolean unique = arguments.remove("-unique");

        if (arguments.isEmpty()) {
            inputs = keysToSet;
        } else {
            for (String key: arguments) {
                inputs.put(key, keysToSet.get(key));
            }
        }

        if (unique) {
            Solver.of().writeFindAllUniqueSolutions(inputs);
        } else {
            Solver.of().writeFindFirstSolutions(inputs);
        }
    }

    private static Map<String, List<Facet>> keyToSet() {
        Map<String, List<Facet>> map = new HashMap<>();

        map.put("-first", FirstSet.FACETS);
        map.put("-second", SecondSet.FACETS);
        map.put("-third", ThirdSet.FACETS);
        map.put("-fourth", FourthSet.FACETS);

        return map;
    }
}
