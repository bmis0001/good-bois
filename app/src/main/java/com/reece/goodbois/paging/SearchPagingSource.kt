package com.reece.goodbois.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reece.goodbois.model.Breed
import com.reece.goodbois.retrofit.DoggieApi
import kotlin.math.ceil

class SearchPagingSource(private val doggieApi: DoggieApi, private val keyword: String) :
    PagingSource<Int, Breed>() {

    override fun getRefreshKey(state: PagingState<Int, Breed>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        return try {
            val limit = 10
            val position = params.key ?: 1
            val response =
                if (keyword.isEmpty())
                    doggieApi.getAllBreeds()
                else
                    doggieApi.getBreedsByName(q = keyword)
            val result = response.body()!!
            val headers = response.headers()
            val pages = ceil(headers.get("pagination-count")!!.toDouble() / limit).toInt()

            if (response.isSuccessful) {
                Log.e("RESPONSE", result.toString())
                return LoadResult.Page(
                    data = result,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == pages) null else position + 1
                )
            } else {
                LoadResult.Error(Throwable("Server error"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}