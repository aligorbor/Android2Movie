package ru.geekbrains.android2.movieapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBar(
    resIdText: Int,
    resIdActionText: Int,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, this.context.getString(resIdText), length)
        .setAction(this.context.getString(resIdActionText), action).show()
}

fun View.showSnackBar(
    text: String,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).show()
}

fun View.showSnackBar(
    resIdText: Int,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, this.context.getString(resIdText), length).show()
}