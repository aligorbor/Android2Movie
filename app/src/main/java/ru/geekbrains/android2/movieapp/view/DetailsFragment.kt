package ru.geekbrains.android2.movieapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentDetailsBinding
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.services.*
import ru.geekbrains.android2.movieapp.utils.showSnackBar
import ru.geekbrains.android2.movieapp.viewmodel.AppStateDetails
import ru.geekbrains.android2.movieapp.viewmodel.DetailsViewModel

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movie = Movie()

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailsViewModel

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getBooleanExtra(INTENT_SERVICE_DATA, false)) {
                true -> fillViews(intent.getParcelableExtra(DETAILS_MOVIE_OUT_EXTRA) ?: Movie())
                else -> {
                    binding.detailsFragmentRootView.showSnackBar(
                        intent.getStringExtra(DETAILS_ERROR_OUT_EXTRA) ?: "",
                        getString(R.string.reload),
                        {
                            getMovieDetailFromRemoteSourceService(movie)
                        })
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { it ->
            movie = it
        }

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
            getMovieDetailFromRemoteSource(movie)
        }
    }

    fun getMovieDetailFromRemoteSourceService(movie: Movie) {
        val intent = Intent(requireContext(), ServiceMovieDetails::class.java)
        intent.putExtra(DETAILS_MOVIE_IN_EXTRA, movie)
        ServiceMovieDetails.start(requireContext(), intent)
    }

    private fun renderData(appState: AppStateDetails) = with(binding) {
        when (appState) {
            is AppStateDetails.Success -> {
                detailsFragmentLoadingLayout.visibility = View.GONE
                detailsFragmentRootView.visibility = View.VISIBLE
                fillViews(appState.movie)
            }
            is AppStateDetails.Loading -> {
                detailsFragmentLoadingLayout.visibility = View.VISIBLE
                detailsFragmentRootView.visibility = View.GONE
            }
            is AppStateDetails.Error -> {
                detailsFragmentLoadingLayout.visibility = View.GONE
                detailsFragmentRootView.visibility = View.VISIBLE
                detailsFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getMovieDetailFromRemoteSource(movie)
                    })
            }
        }
    }

    private fun fillViews(movie: Movie) {
        with(movie) {
            with(binding) {
                detailsTitle.text = title
                detailsVoteAverage.text =
                    String.format(
                        getString(R.string.rating),
                        vote_average,
                        vote_count
                    )
                detailsReleaseDate.text =
                    String.format(
                        getString(R.string.release_date),
                        release_date
                    )
                detailsGenres.text = genres
                detailsDuration.text =
                    String.format(
                        getString(R.string.duration),
                        runtime
                    )
                detailsBudget.text = String.format(
                    getString(R.string.budget),
                    budget
                )
                detailsRevenue.text = String.format(
                    getString(R.string.revenue),
                    revenue
                )
                detailsOverview.text = overview
                detailsOriginalTitle.text = original_title
                Picasso
                    .get()
                    .load(poster_path)
                    .into(detailsPoster)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(loadResultsReceiver)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}