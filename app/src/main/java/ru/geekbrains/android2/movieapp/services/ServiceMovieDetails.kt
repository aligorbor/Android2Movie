package ru.geekbrains.android2.movieapp.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.model.url_connection.MoviesLoader

class ServiceMovieDetails(name: String = "ServiceMovieDetails") : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        val movie: Movie? = intent.getParcelableExtra(DETAILS_MOVIE_IN_EXTRA)
        movie?.let { sendDetailsBroadcast(MoviesLoader.loadMovieDetail(movie)) }
        try {
            movie?.let { sendDetailsBroadcast(MoviesLoader.loadMovieDetail(movie)) }
        } catch (e: Exception) {
            sendErrorBroadcast(e.message ?: "Error")
        }
    }

    private fun sendDetailsBroadcast(movie: Movie) {
        val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true)
        broadcastIntent.putExtra(DETAILS_MOVIE_OUT_EXTRA, movie)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun sendErrorBroadcast(message: String) {
        val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, false)
        broadcastIntent.putExtra(DETAILS_ERROR_OUT_EXTRA, message)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    companion object {
        fun start(context: Context, intent: Intent) {
            enqueueWork(context, ServiceMovieDetails::class.java, jobId, intent)
        }
    }

}