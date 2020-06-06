package com.gitanjali.movielist.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitanjali.movielist.R
import com.gitanjali.movielist.databinding.ActivityMainBinding
import com.gitanjali.movielist.model.Movie
import com.gitanjali.movielist.viewModel.MovieAdapter
import com.gitanjali.movielist.viewModel.MovieViewModel
import java.util.*

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    var movieViewModel:MovieViewModel?=null
    var movieAdapter:MovieAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingObj:ActivityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        movieViewModel=ViewModelProvider(this).get(MovieViewModel::class.java)

        val recyclerView: RecyclerView
        recyclerView=  bindingObj.recycleViewList
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        movieAdapter= MovieAdapter(this)
        recyclerView.adapter=movieAdapter
        getMovieList()
    }//onCreate

    fun getMovieList(){
        movieViewModel!!.getMutableLiveMovieData().observe(this,Observer {
                mv->movieAdapter!!.setMovieList(mv as ArrayList<Movie>)
        })
    }//getMovieList

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                movieAdapter!!.filter!!.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // filter recycler view when text is changed
                movieAdapter!!.filter!!.filter(query)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    override fun onItemClick(movie: Movie) {
        Toast.makeText(this,"Movie Name :  ${movie.movieName}",Toast.LENGTH_SHORT).show()
        Log.e("Movie Name :",movie.movieName.toString())

    }

}
