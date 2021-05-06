package ru.geekbrains.android2.movieapp.model

class RepositoryImpl:Repository  {

    override fun getCategoriesFromLocalStorageRus(): List<Category> {
        return getRussianCategories()
    }

    override fun getCategoriesFromLocalStorageWorld(): List<Category> {
        return getWorldCategories()
    }
}