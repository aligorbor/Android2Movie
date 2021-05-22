package ru.geekbrains.android2.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String = "",
    val movies: MutableList<Movie> = mutableListOf(),
    val id: Int = 0,
    var isRus: Boolean = false
) : Parcelable
