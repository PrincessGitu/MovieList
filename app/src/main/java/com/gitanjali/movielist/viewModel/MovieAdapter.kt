package com.gitanjali.movielist.viewModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitanjali.movielist.R
import com.gitanjali.movielist.databinding.ContentListDataBinding
import com.gitanjali.movielist.model.Movie

/**
 * Created by Gitanjali Ghangale on 6/3/2020.
 */
class MovieAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() , Filterable {


    var mListData = ArrayList<Movie>()
    var filteredListData = ArrayList<Movie>()

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(filteredListData[position],itemClickListener)

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return filteredListData.size
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ContentListDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.content_list_data, parent, false)

        return MovieViewHolder(binding)
    }

    class MovieViewHolder(private val binding: ContentListDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie,itemClickListener: OnItemClickListener) {
            binding.movie=item
            binding.clickListener=itemClickListener
            binding.executePendingBindings()

        }
    }


    fun setMovieList(list: ArrayList<Movie>) {
        mListData = list
        filteredListData = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                var filteredList: ArrayList<Movie> = ArrayList<Movie>()
                if (charString.isEmpty()) {
                    filteredList = mListData
                } else {

                    for (row in mListData) {
                         val searchString=charString.toLowerCase()
                        if (row.movieName!!.toLowerCase().contains(searchString) || row.releaseDate!!.toLowerCase().contains(searchString)
                            || row.verdict!!.toLowerCase().contains(searchString)) {
                            filteredList.add(row)
                        }
                    }

                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredListData = filterResults.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(movie: Movie)
    }

}