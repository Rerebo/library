package com.rerebo.library.retrofit

import retrofit2.http.GET

interface ProductApi {
    @GET("api/v1/artworks?page=1&limit=1&fields=title,image_id,date_display,artist_title,short_description")
    fun getProductById(): Product
}