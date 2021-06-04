package ru.geekbrains.android2.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.databinding.MainActivityBinding
import ru.geekbrains.android2.movieapp.services.NetworkCondition
import ru.geekbrains.android2.movieapp.utils.showSnackBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private var networkCondition = NetworkCondition(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)
        initToolBar()
        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        networkCondition.result = { isAvailable, message ->
            runOnUiThread {
                view.showSnackBar(message)
            }
        }
        networkCondition.register()
    }

    override fun onDestroy() {
        networkCondition.unregister()
        super.onDestroy()
    }

    private fun initToolBar() {
        val toolbar = binding.activityToolbar
        setSupportActionBar(toolbar)
        val fragmentManager = supportFragmentManager
        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount > 0) {
                // show back button
                supportActionBar?.let {
                    it.setDisplayHomeAsUpEnabled(true)
                    it.setHomeButtonEnabled(true)
                    toolbar.setNavigationOnClickListener { v: View? -> onBackPressed() }
                }
            } else {
                supportActionBar?.let {
                    it.setDisplayHomeAsUpEnabled(false)
                    it.setHomeButtonEnabled(false)
                }
            }
        }
    }
}