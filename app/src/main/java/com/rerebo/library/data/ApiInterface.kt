package com.rerebo.library.data
import androidx.annotation.NonNull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/api/v1/artworks?exclude=data&limit=1&fields=title%2Cimage_id%2Cdate_display%2Cartist_title%2Cshort_description")
    suspend fun getDataArr(@Query("page") id : Int): Response<Artic>
}