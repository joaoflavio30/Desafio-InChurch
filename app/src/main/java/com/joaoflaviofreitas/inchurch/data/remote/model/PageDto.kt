package com.joaoflaviofreitas.inchurch.data.remote.model

import com.google.gson.annotations.SerializedName

data class PageDto(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieDto>?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
)
