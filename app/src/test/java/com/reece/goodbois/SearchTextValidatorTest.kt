package com.reece.goodbois

import com.google.common.truth.Truth.assertThat
import com.reece.goodbois.util.Validator
import org.junit.Test

/*
Test class for testing if the Validator class is checking for empty input properly
 */
class SearchTextValidatorTest {

    @Test
    fun `search field is valid`() {
        assertThat(Validator.validateSearchKeyword("dance")).isEqualTo(true)
    }

    @Test
    fun `search field is invalid`() {
        assertThat(Validator.validateSearchKeyword("")).isEqualTo(false)
    }
}