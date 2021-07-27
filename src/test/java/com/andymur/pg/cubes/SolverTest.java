package com.andymur.pg.cubes;

import com.andymur.pg.cubes.data.FirstSet;
import com.andymur.pg.cubes.data.FourthSet;
import com.andymur.pg.cubes.data.SecondSet;
import com.andymur.pg.cubes.data.ThirdSet;
import org.junit.Test;

public class SolverTest {

    @Test
    public void first() {
        Solver.of().printSolution(FirstSet.FACETS);
    }

    @Test
    public void second() {
        Solver.of().printSolution(SecondSet.FACETS);
    }

    @Test
    public void third() {
        Solver.of().printSolution(ThirdSet.FACETS);
    }

    @Test
    public void fourth() {
        Solver.of().printSolution(FourthSet.FACETS);
    }
}