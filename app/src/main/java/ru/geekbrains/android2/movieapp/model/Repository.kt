package ru.geekbrains.android2.movieapp.model

interface Repository {
    fun getCategoriesFromLocalStorageRus(): List<Category>
    fun getCategoriesFromLocalStorageWorld(): List<Category>
}