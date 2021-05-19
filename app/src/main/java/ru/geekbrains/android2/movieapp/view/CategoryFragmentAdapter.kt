package ru.geekbrains.android2.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.model.Movie

class CategoryFragmentAdapter(private var onItemViewClickListener: CategoryFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<CategoryFragmentAdapter.MainViewHolder>() {

    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.apply {
                with(movie) {
                    findViewById<TextView>(R.id.item_title_category).text = title
                    findViewById<TextView>(R.id.item_release_date_category).text = release_date
                    findViewById<TextView>(R.id.item_vote_average_category).text =
                        vote_average.toString()
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(movie)
                    }
                    Picasso
                        .get()
                        .load(poster_path)
                        .into(findViewById<ImageView>(R.id.poster_category))
                }
            }
        }
    }
}