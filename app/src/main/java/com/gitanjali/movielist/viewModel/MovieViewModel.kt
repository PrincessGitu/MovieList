package com.gitanjali.movielist.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gitanjali.movielist.model.Movie
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Gitanjali Ghangale on 6/1/2020.
 */
class MovieViewModel(application: Application) : AndroidViewModel(application) {


    var mutableMovieList=MutableLiveData<List<Movie>>()
    var mListData=ArrayList<Movie>()

    var data="{\n" +
            "\"heroName\":\"Amir Khan\",\n" +
            "\"mList\":[\n" +
            "{\n" +
            "\"movieName\": \"Ghajini\"  ,\n" +
            " \"releaseDate\" :\"25 Dec 2008\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            " {\n" +
            "\"movieName\": \"Rang De Basanti\"  ,\n" +
            " \"releaseDate\" :\"26 Jan 2006\",\n" +
            " \"verdict\":\"Super Hit\"},\n" +
            "  {\n" +
            "\"movieName\": \"3 Idiots\"  ,\n" +
            " \"releaseDate\" :\"24 Dec 2009\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            "  {\n" +
            "\"movieName\": \"Dangal\"  ,\n" +
            " \"releaseDate\" :\"23 Dec 2016\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            "  {\n" +
            "\"movieName\": \"Faana\"  ,\n" +
            " \"releaseDate\" :\"26 May 2006\",\n" +
            " \"verdict\":\"Super Hit\"},\n" +
            " {\n" +
            "\"movieName\": \"Dhoom 3\"  ,\n" +
            " \"releaseDate\" :\"20 Dec 2013\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            " {\n" +
            "\"movieName\": \"Qayamat Se Qayamat Tak\"  ,\n" +
            " \"releaseDate\" :\"1 Mar 1988\",\n" +
            " \"verdict\":\"Super Hit\"}, \n" +
            "  {\n" +
            "\"movieName\": \"PK\"  ,\n" +
            " \"releaseDate\" :\"19 Dec 2014\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            " {\n" +
            "\"movieName\": \"Dil\"  ,\n" +
            " \"releaseDate\" :\"22 Jun 1990\",\n" +
            " \"verdict\":\"Super Hit\"}, \n" +
            " {\n" +
            "\"movieName\": \"Raja Hindustani\"  ,\n" +
            " \"releaseDate\" :\"15 Nov 1996\",\n" +
            " \"verdict\":\"All Time Blockbuster\"},\n" +
            " {\n" +
            "\"movieName\": \"Taare Zameen Par\"  ,\n" +
            " \"releaseDate\" :\"21 Dec 2007\",\n" +
            " \"verdict\":\"Super Hit\"}\n" +
            "]\n" +
            "}"


    fun getMutableLiveMovieData(): MutableLiveData<List<Movie>> {
        Log.e("Response","="+data)
        try {
            val jsonObject=JSONObject(data)
            val jsonArray=jsonObject.getJSONArray("mList")
            if(jsonArray.length()>0){
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)

                    val mList=Movie()
                    mList.movieName=obj.getString("movieName")
                    mList.releaseDate=obj.getString("releaseDate")
                    mList.verdict=obj.getString("verdict")
                    mListData.add(mList)

                }
                mutableMovieList.value=mListData

            }
        } catch (e: Exception) {
        }

        return mutableMovieList
    }
}