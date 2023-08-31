package org.tensorflow.lite.examples.objectdetection.utils.network

import org.tensorflow.lite.examples.objectdetection.utils.network.response.SearchResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Basem on 20/5/2021.
 * this interface contains all api functions that we use to connect with webservice
 */
interface ServiceApi {
    @POST("images")
    fun search(
        @Header("X-API-KEY") key: String? = "431b7933d94961365f3ff9f2c472a3c2d442e69a",
        @Header("Content-Type") content: String? = "application/json",
        @Query("gl") country: String? = "eg",
        @Query("q") query: String,
    ): Call<SearchResponse>
}