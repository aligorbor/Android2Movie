package ru.geekbrains.android2.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.model.Category

class MainFragmentCategoryAdapter(
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener,
    private var onCategoryClickListener: MainFragment.OnCategoryClickListener
) :
    RecyclerView.Adapter<MainFragmentCategoryAdapter.MainViewHolder>() {

    private var catgoryData: List<Category> = listOf()

    fun setCategory(data: List<Category>) {
        catgoryData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(catgoryData[position])
    }

    override fun getItemCount(): Int {
        return catgoryData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(category: Category) {
            itemView.apply {
                findViewById<TextView>(R.id.textCategory).apply {
                    text = category.name
                    setOnClickListener {
                        onCategoryClickListener?.onCategoryClick(category)
                    }
                }
                findViewById<RecyclerView>(R.id.mainFragmentRecyclerView).adapter =
                    MainFragmentAdapter(onItemViewClickListener).also {
                        it.setMovie(category.movies)
                    }
            }
        }
    }
}