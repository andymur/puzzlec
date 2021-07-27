package com.andymur.pg.cubes;

import com.andymur.pg.cubes.domain.Unfolding;
import com.andymur.pg.cubes.domain.facet.Facet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author andymur
 */
final class Solver {

    private Solver() {
    }

    static Solver of() {
        return new Solver();
    }

    public void writeFindFirstSolutions(Map<String, List<Facet>> inputs) throws IOException {

        for (Map.Entry<String, Optional<Unfolding>> solution: findFirstSolutions(inputs).entrySet()) {
            String value = solution.getValue().map(Unfolding::toString).orElse("NO SOLUTION FOUND");
            String fileName = buildFileName(solution.getKey(), false);
            writeOneSolution(fileName, value);
        }
    }

    public void printSolution(List<Facet> input) {
        findFirstSolution(input).ifPresent(Unfolding::printOut);
    }

    private Map<String, Optional<Unfolding>> findFirstSolutions(Map<String, List<Facet>> inputs) {
        Map<String, Optional<Unfolding>> result = new HashMap<>();

        for (String setName: inputs.keySet()) {
            result.put(setName, findFirstSolution(inputs.get(setName)));
        }

        return result;
    }

    private Optional<Unfolding> findFirstSolution(List<Facet> inputs) {
        UnfoldingFinder finder = UnfoldingFinder.of();
        return finder.findFirst(inputs);
    }

    public void writeFindAllUniqueSolutions(Map<String, List<Facet>> inputs) throws IOException {
        UnfoldingFinder finder = UnfoldingFinder.of();

        for (Map.Entry<String, List<Facet>> input: inputs.entrySet()) {
            Collection<String> results = finder
                    .unique()
                    .find(input.getValue()).stream().map(Unfolding::toString).collect(Collectors.toList());

            String fileName = buildFileName(input.getKey(), true);
            writeUniqueSolutions(fileName, results);
        }
    }

    private String buildFileName(String setName, boolean unique) {
        if (unique) {
            return setName.replace("-", "").concat("-unique-solutions.txt");
        } else {
            return setName.replace("-", "").concat("-solution.txt");
        }
    }

    void writeOneSolution(String fileName, String result) throws IOException {
        Files.write(Paths.get(fileName), Arrays.asList(result));
    }

    void writeUniqueSolutions(String fileName, Collection<String> results) throws IOException {
        Files.write(Paths.get(fileName), results);
    }
}
