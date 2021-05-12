package ru.geekbrains.android2.movieapp.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.FragmentMainBinding
import ru.geekbrains.android2.movieapp.model.Category
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.utils.showSnackBar
import ru.geekbrains.android2.movieapp.viewmodel.AppState
import ru.geekbrains.android2.movieapp.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        movie.isRus = isDataSetRus
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private val onCategoryClickListener = object : OnCategoryClickListener {
        override fun onCategoryClick(category: Category) {
            Toast.makeText(context, category.name, Toast.LENGTH_SHORT).show()
        }
    }

    private val adapterCategory =
        MainFragmentCategoryAdapter(onItemViewClickListener, onCategoryClickListener)


    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentCategoryRecyclerView.adapter = adapterCategory
        binding.mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            getLiveData().observe(viewLifecycleOwner, {
                renderData(it)
            })
            getCategoriesFromRemoteSource(isDataSetRus)
        }
    }

    private fun changeMovieDataSet() {
        if (isDataSetRus) {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.also {
            isDataSetRus = !isDataSetRus
            viewModel.getCategoriesFromRemoteSource(isDataSetRus)
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapterCategory.setCategory(appState.categoryData)
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    "Reload",
                    {
                        viewModel.getCategoriesFromRemoteSource(isDataSetRus)
                    })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment, menu)
        val search = menu.findItem(R.id.action_search)
        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}