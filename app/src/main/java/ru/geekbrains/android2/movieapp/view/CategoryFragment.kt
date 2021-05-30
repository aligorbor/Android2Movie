package ru.geekbrains.android2.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentCategoryBinding
import ru.geekbrains.android2.movieapp.model.Category
import ru.geekbrains.android2.movieapp.model.Movie

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        movie.isRus = category.isRus
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private var category = Category()

    private val adapter =
        CategoryFragmentAdapter(onItemViewClickListener)

    companion object {
        const val BUNDLE_EXTRA = "category"
        fun newInstance(bundle: Bundle): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Category>(BUNDLE_EXTRA)?.let {
            category = it
        }
        binding.categoryFragmentRecyclerView.adapter = adapter
        adapter.setMovie(category.movies)
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }
}