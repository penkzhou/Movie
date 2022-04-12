package com.oldautumn.movie

import com.oldautumn.movie.utils.Utils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilsUnitTest {
    @Test
    fun testFetchFirstCharacter() {
        assertTrue(Utils.fetchFirstCharacter("Hello World") == "HW")
        assertTrue(Utils.fetchFirstCharacter("Hello World You") == "HW")
        assertFalse(Utils.fetchFirstCharacter("Hello World") == "H")
    }
}