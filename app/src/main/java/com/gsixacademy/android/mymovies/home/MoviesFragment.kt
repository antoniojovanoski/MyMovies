package com.gsixacademy.android.mymovies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gsixacademy.android.mymovies.R
import com.gsixacademy.android.mymovies.api.ApiServiceBuilder
import com.gsixacademy.android.mymovies.api.MovieDatabaseApi
import com.gsixacademy.android.mymovies.models.MovieListResponse
import kotlinx.android.synthetic.main.movie_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list_fragment,container,false)

        val request = ApiServiceBuilder.buildService(MovieDatabaseApi::class.java)

        val category = arguments?.getString("category")
        val call = request.getMovies(category,1)

        call.enqueue(object : Callback<MovieListResponse> {
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable){
                Toast.makeText(activity,"Error",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>) {
                //Posle proverkata za dali e uspesen povikot,
                if (response.isSuccessful){
                    var movieResponse = response.body()
                    var movieList = movieResponse?.results
                    //se pravi proverka dali dobienata lista od server e null
                    if (movieList != null){
                        //inicijalizacija na adapterot za da se prikazat filmovite,i vo konstruktor ja stavame dobienata lista i kreirame nov click event.
                        var movieListAdapter = MoviesListAdapter(movieList){
                            //proverka na onClick eventot od adapter
                            if (it is MoviesOnClickEvent.onMovieClicked){
                                //prikaz na poraka so naslov na kliknatiot film
                                Toast.makeText(activity,"Title : " + it.movie.title,Toast.LENGTH_SHORT).show()
                            }
                        }
                        //mu setirame layout menadzer na recyclerView vo ovoj slucaj e grid menadzer zatoa sto ke prikazuvame po dva filma vo linija
                        recycler_view_movies.layoutManager = GridLayoutManager(context,2)
                        //setiranje adapter na recyclerView
                        recycler_view_movies.adapter = movieListAdapter
                    }

                }
            }
        })


        return view;
    }
}