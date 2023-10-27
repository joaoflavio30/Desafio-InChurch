package com.joaoflaviofreitas.inchurch.data.remote.model

data class ResponseApiDto(
    val page: Int,
    val results: List<ResponseMovie>,
    val totalResults: Int,
    val totalPages: Int,
)
