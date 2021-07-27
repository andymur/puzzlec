package com.andymur.pg.cubes.helper;

import org.junit.Assert;
import org.junit.Test;

import static com.andymur.pg.cubes.helper.RowHelper.ENCODED_BLOCK;
import static com.andymur.pg.cubes.helper.RowHelper.ENCODED_EMPTY_BLOCK;

public class RowHelperTest {

    @Test
    public void testRowToString() throws Exception {
        Assert.assertEquals("  [][][]  ",
                RowHelper.rowToString(
                        new int[]{ENCODED_EMPTY_BLOCK, ENCODED_BLOCK, ENCODED_BLOCK, ENCODED_BLOCK, ENCODED_EMPTY_BLOCK}
                )
        );
    }

    @Test
    public void testEncodeRow() throws Exception {
        Assert.assertArrayEquals(
                new int[]{ENCODED_EMPTY_BLOCK, ENCODED_BLOCK, ENCODED_BLOCK, ENCODED_BLOCK, ENCODED_EMPTY_BLOCK},
                RowHelper.encodeRow("  [][][]  ")
        );
    }
}