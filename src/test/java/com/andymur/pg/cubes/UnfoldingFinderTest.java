package com.andymur.pg.cubes;

import com.andymur.pg.cubes.data.FirstSet;
import com.andymur.pg.cubes.data.FourthSet;
import com.andymur.pg.cubes.data.SecondSet;
import com.andymur.pg.cubes.data.ThirdSet;
import com.andymur.pg.cubes.domain.Unfolding;
import com.andymur.pg.cubes.domain.facet.Facet;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

public class UnfoldingFinderTest {

    @Test
    public void testFindFirstFromFirstSet() {
        UnfoldingFinder.of().findFirst(
                //new ArrayList<>(Arrays.asList(first, second, third, fourth, fifth, sixth))
                FirstSet.FACETS
        ).ifPresent(
                Unfolding::printOut
        );
    }

    @Test
    public void testFindFirstFromSecondSet() {
        UnfoldingFinder.of().findFirst(
                //new ArrayList<>(Arrays.asList(first, second, third, fourth, fifth, sixth))
                SecondSet.FACETS
        ).ifPresent(
                Unfolding::printOut
        );
    }

    @Test
    public void testFindFirstFromThirdSet() {
        UnfoldingFinder.of().findFirst(
                ThirdSet.FACETS
        ).ifPresent(
                Unfolding::printOut
        );
    }

    @Test
    public void testFindFirstFromFourthSet() {
        UnfoldingFinder.of().findFirst(
                FourthSet.FACETS
        ).ifPresent(
                Unfolding::printOut
        );
    }
}