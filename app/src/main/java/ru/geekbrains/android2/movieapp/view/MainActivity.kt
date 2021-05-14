package ru.geekbrains.android2.movieapp.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        setSupportActionBar(binding.activityToolbar)
    }
}