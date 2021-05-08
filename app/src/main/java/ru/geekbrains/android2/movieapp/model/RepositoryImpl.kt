package ru.geekbrains.android2.movieapp.model

class RepositoryImpl : Repository {

    override fun getCategoriesFromLocalStorageRus() = getRussianCategories()

    override fun getCategoriesFromLocalStorageWorld() = getWorldCategories()
}