package ru.geekbrains.android2.movieapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Persons(
    var name: String = "",
    var persons: MutableList<Person> = mutableListOf(),
    var id: Int = 0,
    var isRus: Boolean = false,
    var page: Int = 1,
    var total_pages: Int = 1,
    var total_results: Int = 0
) : Parcelable
