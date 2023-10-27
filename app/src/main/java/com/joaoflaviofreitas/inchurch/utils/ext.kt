package com.joaoflaviofreitas.inchurch.utils // ktlint-disable filename

import androidx.appcompat.widget.SearchView
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val decimalFormat = DecimalFormat("#.#")
val displayDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

fun ResponseMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        backdropPath = this.backdropPath ?: "",
        genres = this.genres,
        isAdult = this.adult,
        posterPath = this.posterPath ?: "",
        overview = this.overview,
        releaseDate = if (!this.releaseDate.isNullOrEmpty()) {
            SimpleDateFormat("yyyy-MM-dd").parse(
                releaseDate,
            )
        } else {
            Date()
        },
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage,
        title = this.title,
        popularity = this.popularity,
        voteCount = this.voteCount,
        video = this.video,
        voteAverage = decimalFormat.format(this.voteAverage).replace(",", ".").toDouble(),
    )
}

fun ResponseGenre.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name,
    )
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String?> {
    val query = MutableStateFlow<String?>(null)

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            query.value = newText
            return true
        }
    })

    return query
}
