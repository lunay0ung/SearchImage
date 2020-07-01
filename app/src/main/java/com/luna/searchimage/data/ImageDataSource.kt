package com.luna.searchimage.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PageKeyedDataSource
import com.luna.searchbooks.api.ApiService
import com.luna.searchbooks.api.ApiServiceBuilder
import com.luna.searchbooks.api.ImageSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImageDataSource(
    context: Context,
    val keyword: String
) : PageKeyedDataSource<Int, Image>() {

    private val TAG = ImageDataSource::class.java.simpleName
    private val mCtx = context

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 10
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchImage(params.key, keyword)
        call.enqueue(object : Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.images
                    val key = if (params.key > 1) params.key - 1 else 0
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }
            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadBefore onFailure: ${t.message}")
            }
        })
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchImage(FIRST_PAGE, keyword)
        call.enqueue(object : Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.images
                    val meta = apiResponse.meta

                    if(meta!!.totalCount == 0) {
                        Toast.makeText(mCtx, "검색 결과가 없습니다.", Toast.LENGTH_LONG).show()
                    }
                    else {
                        //if(ImageL.SHOW_SEARCH_RESULT_MESSAGE)
                            Toast.makeText(mCtx, "총 ${meta.totalCount}건의 이미지가 조회되었습니다.",
                                Toast.LENGTH_LONG
                            ).show()
                    }

                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }
                }
            }
            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                //todo 오류 발생 시 예외처리 필요
                Log.e(TAG, "loadInitial onFailure: ${t.message}")
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchImage(params.key, keyword)
        call.enqueue(object : Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.images
                    val meta = apiResponse.meta

                    val key = params.key + 1
                    responseItems?.let {
                        if(!apiResponse.meta!!.isEnd) {
                            callback.onResult(responseItems, key)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadAfter onFailure: ${t.message}")
            }
        })
    }

}
