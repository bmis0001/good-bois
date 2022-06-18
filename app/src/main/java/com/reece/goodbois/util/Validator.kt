package com.reece.goodbois.util

import java.util.regex.Pattern

/*
Util class to validate user input and url
 */
object Validator {

    private const val URL_REGEX =
        "http(s?)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./]*)+\\.(?:[gG][iI][fF]|[jJ][pP][gG]|[jJ][pP][eE][gG]|[pP][nN][gG]|[bB][mM][pP])"

    //To check if the user has input any value before making the API request
    fun validateSearchKeyword(keyword: String? = null): Boolean {
        return !keyword.isNullOrEmpty()
    }

    //To check if the url constructed to fetch image is a valid url or not
    fun validateUrl(url: String): Boolean {
        return Pattern.compile(URL_REGEX).matcher(url).find()
    }
}