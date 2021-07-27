package com.andymur.pg.cubes.helper;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author andymur
 */
public final class RowHelper {

    public static final int ROW_LENGTH = 5;


    static final int ENCODED_EMPTY_BLOCK = 0;
    static final int ENCODED_BLOCK = 1;

    static final String EMPTY_BLOCK = "  ";
    static final String BLOCK = "[]";
    private static final int SYMBOLS_PER_BLOCK = 2;
    private static final int ROW_STRING_LENGTH = SYMBOLS_PER_BLOCK * ROW_LENGTH;


    private RowHelper(){
        throw new IllegalStateException("RowHelper instantiation is not allowed");
    }

    /**
     * Transforms bit state into string representation
     * @param encodedRow bit state row
     * @return string representation of a row
     */
    public static String rowToString(int[] encodedRow) {
        return Arrays.stream(encodedRow).boxed()
                .map(RowHelper::writeBlock)
                .collect(Collectors.joining());
    }

    /**
     * Transforms string state into a row of ones and zeros
     * @param row string representation
     * @return arrays of zeros and ones
     */
    public static int[] encodeRow(String row) {

        if (row.length() != ROW_STRING_LENGTH) {
            throw new IllegalArgumentException(String.format("Edge string representation cannot consist of %d symbols",
                    row.length()));
        }

        return IntStream.range(0, 5)
                .map(i -> readBlock(row.substring(i * SYMBOLS_PER_BLOCK, i * SYMBOLS_PER_BLOCK + 2)))
                .toArray();
    }

    private static String writeBlock(int block) {
        switch (block) {
            case 0:
                return EMPTY_BLOCK;
            case 1:
                return BLOCK;
            default:
                throw new IllegalArgumentException(
                        String.format("Block value is incorrect: %d", block)
                );
        }
    }

    private static int readBlock(String block) {
        switch (block) {
            case EMPTY_BLOCK:
                return ENCODED_EMPTY_BLOCK;
            case BLOCK:
                return ENCODED_BLOCK;
            default:
                throw new IllegalArgumentException(
                        String.format("Symbols that formed block are incorrect: %s", block)
                );
        }
    }
}
