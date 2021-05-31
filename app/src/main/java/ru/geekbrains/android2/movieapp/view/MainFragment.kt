package ru.geekbrains.android2.movieapp.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.contacts.ContactsFragment
import ru.geekbrains.android2.movieapp.databinding.FragmentMainBinding
import ru.geekbrains.android2.movieapp.interactors.StringsInteractorImpl
import ru.geekbrains.android2.movieapp.map.MapsFragment
import ru.geekbrains.android2.movieapp.map.MapsFragment.Companion.BUNDLE_ADDRESS_STR
import ru.geekbrains.android2.movieapp.model.Category
import ru.geekbrains.android2.movieapp.model.Movie
import ru.geekbrains.android2.movieapp.utils.showSnackBar
import ru.geekbrains.android2.movieapp.view.PeoplesFragment.Companion.BUNDLE_ADULT
import ru.geekbrains.android2.movieapp.view.PeoplesFragment.Companion.BUNDLE_IS_RUS
import ru.geekbrains.android2.movieapp.viewmodel.AppState
import ru.geekbrains.android2.movieapp.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            openFragment(DetailsFragment.newInstance(Bundle().apply {
                movie.isRus = isDataSetRus
                putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
            }))
        }
    }

    private val onCategoryClickListener = object : OnCategoryClickListener {
        override fun onCategoryClick(category: Category) {
            openFragment(CategoryFragment.newInstance(Bundle().apply {
                category.isRus = isDataSetRus
                putParcelable(CategoryFragment.BUNDLE_EXTRA, category)
            }))
        }
    }

    private val setFavoriteToMovie = object : SetFavoriteToMovie {
        override fun setFavorite(movie: Movie) {
            if (movie.isFavorite) {
                viewModel.saveMovieToFavorite(movie)
            } else {
                viewModel.deleteMovieFromFavorite(movie)
            }
        }
    }

    private val adapterCategory =
        MainFragmentCategoryAdapter(
            onItemViewClickListener,
            onCategoryClickListener,
            setFavoriteToMovie
        )


    private var isDataSetRus: Boolean = true
    private var adult: Boolean = false

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
            restorePreferences()
            getCategoriesFromRemoteSource(
                isDataSetRus,
                StringsInteractorImpl(requireContext()),
                adult
            )
        }
    }

    private fun changeMovieDataSet() {
        if (isDataSetRus) {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }.also {
            isDataSetRus = !isDataSetRus
            viewModel.getCategoriesFromRemoteSource(
                isDataSetRus,
                StringsInteractorImpl(requireContext()),
                adult
            )
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapterCategory.setCategory(appState.categoryData)
            }
            is AppState.SuccessCategory -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                openFragment(CategoryFragment.newInstance(Bundle().apply {
                    putParcelable(CategoryFragment.BUNDLE_EXTRA,
                        appState.category.apply { isRus = isDataSetRus })
                }))
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentRootView.showSnackBar(
                    appState.error.message ?: "",
                    getString(R.string.reload),
                    {
                        viewModel.getCategoriesFromRemoteSource(
                            isDataSetRus,
                            StringsInteractorImpl(requireContext()),
                            adult
                        )
                    })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment, menu)
        val adultItem = menu.findItem(R.id.action_adult)
        adultItem.isChecked = adult
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                viewModel.getMoviesHistory()
                true
            }
            R.id.action_favorite -> {
                viewModel.getMoviesFavorite()
                true
            }
            R.id.action_persons -> {
                openFragment(PeoplesFragment.newInstance(Bundle().apply {
                    putBoolean(BUNDLE_IS_RUS, isDataSetRus)
                    putBoolean(BUNDLE_ADULT, adult)
                }))
                true
            }
            R.id.action_adult -> {
                item.isChecked = !item.isChecked
                adult = item.isChecked
                savePreferences()
                viewModel.getCategoriesFromRemoteSource(
                    isDataSetRus,
                    StringsInteractorImpl(requireContext()),
                    adult
                )
                true
            }
            R.id.action_content_provider -> {
                openFragment(ContactsFragment.newInstance())
                true
            }
            R.id.action_google_maps -> {
                openFragment(MapsFragment.newInstance(Bundle().apply {
                    putString(BUNDLE_ADDRESS_STR, "")
                }))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun restorePreferences() {
        activity?.let {
            adult = it.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                .getBoolean(adultKey, false)
        }
    }

    private fun savePreferences() {
        activity?.let {
            val preferences = it.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(adultKey, adult)
            editor.apply()
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    interface SetFavoriteToMovie {
        fun setFavorite(movie: Movie)
    }

    companion object {
        private const val adultKey = "adultKey"
        const val preferencesName = "MainPreferences"
        fun newInstance() =
            MainFragment()
    }
}