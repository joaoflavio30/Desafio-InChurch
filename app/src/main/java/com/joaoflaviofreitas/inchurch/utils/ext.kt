package com.joaoflaviofreitas.inchurch.utils // ktlint-disable filename

import androidx.appcompat.widget.SearchView
import com.joaoflaviofreitas.inchurch.data.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
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
        backdropPath = this.backdrop_path ?: "",
        genres = this.genres,
        isAdult = this.adult,
        posterPath = this.poster_path ?: "",
        overview = this.overview,
        releaseDate = if (!this.release_date.isNullOrEmpty()) {
            SimpleDateFormat("yyyy-MM-dd").parse(
                release_date,
            )
        } else {
            Date()
        },
        originalTitle = this.original_title,
        originalLanguage = this.original_language,
        title = this.title,
        popularity = this.popularity,
        voteCount = this.vote_count,
        video = this.video,
        voteAverage = decimalFormat.format(this.vote_average).replace(",", ".").toDouble(),
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
