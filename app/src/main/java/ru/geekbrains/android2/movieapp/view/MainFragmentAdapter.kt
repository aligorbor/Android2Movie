package ru.geekbrains.android2.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.model.Movie

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
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
            itemView.findViewById<TextView>(R.id.item_title).text = movie.title
            itemView.findViewById<TextView>(R.id.item_release_date).text = movie.release_date
            itemView.findViewById<TextView>(R.id.item_vote_average).text =
                movie.vote_average.toString()

            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(movie)
            }
        }
    }
}