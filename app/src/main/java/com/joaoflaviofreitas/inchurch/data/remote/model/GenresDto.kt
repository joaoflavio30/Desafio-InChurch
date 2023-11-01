package com.joaoflaviofreitas.inchurch.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenresDto(
    @SerializedName("genres") val genres: List<GenreDto>?,
)
