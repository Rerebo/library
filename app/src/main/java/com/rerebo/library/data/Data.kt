package com.rerebo.library.data
data class Artic(
    val data: Array<Data> = arrayOf(Data())
)
data class Data(
    val artist_title: String="",
    val date_display: String="",
    val image_id: String?=null,
    val short_description: String?=null,
    val title: String=""
)