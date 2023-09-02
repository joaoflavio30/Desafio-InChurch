package com.joaoflaviofreitas.inchurch.data.model

data class ResponseApi(
    val page: Int,
    val results: List<ResponseMovie>,
    val totalResults: Int,
    val totalPages: Int,
)
