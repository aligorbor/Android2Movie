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
import ru.geekbrains.android2.movieapp.viewmodel.AppState
import ru.geekbrains.android2.movieapp.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
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
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentCategoryRecyclerView.adapter = adapterCategory

        binding.mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getCategoriesFromLocalSourceRus()
    }

    private fun changeMovieDataSet() {
        if (isDataSetRus) {
            viewModel.getCategoriesFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getCategoriesFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapterCategory.setCategory(appState.categoryData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.mainFragmentFAB, getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) {
                        if (isDataSetRus)
                            viewModel.getCategoriesFromLocalSourceWorld()
                        else
                            viewModel.getCategoriesFromLocalSourceRus()
                    }
                    .show()
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
        //     super.onCreateOptionsMenu(menu, inflater)
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