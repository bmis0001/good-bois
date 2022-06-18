package com.reece.goodbois

import com.google.common.truth.Truth
import com.reece.goodbois.util.Validator
import org.junit.Test

/*
Test class for testing if the Validator class is validating the url properly
 */
class UrlValidatorTest {
    private val validUrl = "https://cdn2.thedogapi.com/images/40bvxOUyl.jpg"

    @Test
    fun `url pattern is valid`() {
        Truth.assertThat(Validator.validateSearchKeyword(validUrl)).isEqualTo(true)
    }

    @Test
    fun `url pattern is invalid`() {
        Truth.assertThat(Validator.validateUrl("tiktok")).isEqualTo(false)
    }
}