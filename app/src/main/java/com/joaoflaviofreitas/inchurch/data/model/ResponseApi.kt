package com.joaoflaviofreitas.inchurch.data.model

data class ResponseApi(
    val page: Int,
    val results: List<ResponseMovie>,
    val total_results: Int,
    val total_pages: Int
)