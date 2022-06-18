package com.reece.goodbois

import com.google.common.truth.Truth.assertThat
import com.reece.goodbois.model.Breed
import com.reece.goodbois.retrofit.DoggieApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader

/*
Test class for testing if the retrofit is fetching and parsing the data
 */
@RunWith(JUnit4::class)
class SearchRequestTest {
    private val server = MockWebServer()
    private lateinit var doggieApi: DoggieApi

    @Before
    fun onStart() {
        server.start(8080)
        doggieApi = Retrofit.Builder().baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(DoggieApi::class.java)
    }

    @Test
    fun `should fetch and parse images correctly`() {
        server.enqueue(MockResponse().setBody(mockResponse("search_response_success.json")))
        runBlocking {
            val response = doggieApi.getAllBreeds()
            assertThat(response.body()?.first()).isInstanceOf(Breed::class.java)
        }
    }

    @Test
    fun `should fetch and parse images incorrectly`() {
        server.enqueue(MockResponse().setBody(mockResponse("search_response_failure.json")))
        runBlocking {
            val response = doggieApi.getAllBreeds()
            assertThat(response.body()).isEmpty()
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }


    //Helper function to read the json file to mock response
    private fun mockResponse(fileName: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName))
        val content = reader.readText()
        reader.close()
        return content
    }
}