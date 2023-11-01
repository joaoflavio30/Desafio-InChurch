package com.joaoflaviofreitas.inchurch.utils // ktlint-disable filename

import androidx.appcompat.widget.SearchView
import com.joaoflaviofreitas.inchurch.data.remote.model.GenreDto
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

val decimalFormat = DecimalFormat("#.#")
val displayDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
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
