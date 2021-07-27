package com.andymur.pg.cubes.domain.facet;

import com.andymur.pg.cubes.domain.Edge;
import com.andymur.pg.cubes.helper.RowHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.andymur.pg.cubes.helper.RowHelper.ROW_LENGTH;

/**
 * Represents one of six planes of the cube.
 *
 * Contains for edges or state as a matrix of empty and non-empty blocks.
 *
 * @author andymur
 */
public class Facet {

    private List<Edge> edges;
    private int[][] state;

    /**
     * Creates facet from string representation
     *
     * @param lines string representations, when two spaces are empty block and [] is non-empty
     */
    public Facet(String[] lines) {

        if (lines.length != ROW_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Facet string representation cannot consist of %d rows", lines.length)
                    );
        }

        state = Arrays.stream(lines).map(RowHelper::encodeRow).toArray(int[][]::new);
        initEdges();
    }

    private Facet(int[][] state) {
        this.state = new int[ROW_LENGTH][];
        for (int i = 0; i < ROW_LENGTH; i++) {
            this.state[i] = Arrays.copyOf(state[i], ROW_LENGTH);
        }
        initEdges();
    }

    private void initEdges() {
        Edge northEdge = new Edge(state[0]);
        Edge southEdge = new Edge(state[ROW_LENGTH - 1]);
        Edge westEdge = new Edge(IntStream.range(0, 5).map(i -> state[i][0]).toArray());
        Edge eastEdge = new Edge(IntStream.range(0, 5).map(i -> state[i][ROW_LENGTH - 1]).toArray());

        edges = Arrays.asList(northEdge, eastEdge, southEdge, westEdge);
    }

    @Override
    public String toString() {
        return Arrays.stream(state)
                .map(RowHelper::rowToString)
                .collect(Collectors.joining("\n"));
    }

    public String[] toStrings() {
        return Arrays.stream(state)
                .map(RowHelper::rowToString).toArray(size -> new String[ROW_LENGTH]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Facet facet = (Facet) o;

        for (int i = 0; i < RowHelper.ROW_LENGTH; i++) {
            if (!Arrays.equals(state[i], facet.state[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return state != null ? stateHashCode() : 0;
    }

    public Edge edge(FacetSide side) {
        return edges.get(side.getIndex());
    }

    /**
     * Rotates facet clock wise
     * @return clock wise rotated facet
     */
    public Facet rotate() {
        int[][] rotatedState = new int[ROW_LENGTH][];

        for (int i = 0; i < ROW_LENGTH; i++) {
            rotatedState[i] = new int[ROW_LENGTH];
        }

        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {
                rotatedState[i][j] = state[ROW_LENGTH - (j + 1)][i];
            }
        }

        return new Facet(rotatedState);
    }

    /**
     * Turns facet to another side about horizontal axis
     * @return pivot facet turned about horizontal axis
     */
    public Facet upturn() {
        int[][] turnedState = new int[ROW_LENGTH][];

        for (int i = 0; i < ROW_LENGTH; i++) {
            turnedState[i] = state[ROW_LENGTH - (i + 1)];
        }

        return new Facet(turnedState);
    }

    /**
     * Rotates facet clock wise n-times
     * @param times number of rotations
     * @return n-times clock wise rotated facet
     */
    public Facet rotate(int times) {
        int number = times % 4;
        Facet rotated = copy();

        switch (number) {
            case 1:
                return rotate();
            case 2:
                return rotate().rotate();
            case 3:
                return rotate().rotate().rotate();
        }

        return rotated;
    }

    /**
     * Returns facet's copy
     * @return facet's copy
     */
    public Facet copy() {
        return new Facet(this.state);
    }

    /**
     * Returns facet oriented to the side and upturned if needed
     * @param side of orientation
     * @param isUpturned do we need to upturn
     * @return facet oriented to the side and upturned if needed
     */
    public FacetOrientation orientation(FacetSide side, boolean isUpturned) {
        return new FacetOrientation(copy(), side, isUpturned);
    }

    private int stateHashCode() {
        int sum = 1;

        for (int[] encodedRow: state) {
            sum = sum * 31 + Arrays.stream(encodedRow).sum();
        }

        return sum;
    }
}
