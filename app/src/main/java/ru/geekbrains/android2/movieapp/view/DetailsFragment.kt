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
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movie != null) {
            binding.detailsTitle.text = movie.title
            binding.detailsVoteAverage.text =
                String.format(getString(R.string.rating), movie.vote_average)
            binding.detailsReleaseDate.text =
                String.format(getString(R.string.release_date), movie.release_date)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}