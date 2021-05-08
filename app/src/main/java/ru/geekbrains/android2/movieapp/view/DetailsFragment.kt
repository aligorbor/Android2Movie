package ru.geekbrains.android2.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentDetailsBinding
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.viewmodel.MainViewModel

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { movie->
            with(movie){
                with(binding){
                    detailsTitle.text = title
                    detailsVoteAverage.text =
                        String.format(getString(R.string.rating), vote_average, vote_count)
                    detailsReleaseDate.text =
                        String.format(getString(R.string.release_date), release_date)
                    detailsGenres.text = genres
                    detailsDuration.text =
                        String.format(getString(R.string.duration), duration)
                    detailsBudget.text = String.format(getString(R.string.budget), budget)
                    detailsRevenue.text = String.format(getString(R.string.revenue), revenue)
                    detailsOverview.text = overview
                    detailsOriginalTitle.text = original_title
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}