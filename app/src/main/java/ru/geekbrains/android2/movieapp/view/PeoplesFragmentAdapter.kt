package ru.geekbrains.android2.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geekbrains.android2.movieapp.R
import ru.geekbrains.android2.movieapp.model.Person
import ru.geekbrains.android2.movieapp.model.Persons

class PeoplesFragmentAdapter(private var onItemViewClickListener: PeoplesFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<PeoplesFragmentAdapter.MainViewHolder>() {

    private var persons: Persons = Persons()

    fun setPeople(data: Persons) {
        persons = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_peoples_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(persons.persons[position])
    }

    override fun getItemCount(): Int {
        return persons.persons.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(person: Person) {
            itemView.apply {
                with(person) {
                    findViewById<TextView>(R.id.item_title_peoples).text = name
                    findViewById<TextView>(R.id.item_vote_average_peoples).text =
                        popularity.toString()
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(person)
                    }
                    Picasso
                        .get()
                        .load(profile_path)
                        .into(findViewById<ImageView>(R.id.poster_peoples))
                }
            }
        }
    }
}