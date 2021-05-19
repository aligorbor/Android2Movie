package ru.geekbrains.android2.movieapp.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
