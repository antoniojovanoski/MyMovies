package com.gsixacademy.android.mymovies.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsixacademy.android.mymovies.R
import com.gsixacademy.android.mymovies.models.MovieResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*

//kreiranje na adapterot, kade sto vo konstruktor imame lista od filmovi i click event
class MoviesListAdapter(val moviesList: ArrayList<MovieResult>, val moviesListOnClickEvent: (MoviesOnClickEvent) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //kreiranje na xml layout my movie list i negovo postavuvanje vo MyViewHolder
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item,parent,false))
    }
    //getITemCount e funkcija od samiot recyclerView adapter i sekogas mora da ja imame
    //getItemsCount vrakja kolku items ke imame prikazano i zatoa ni e setirano da vrati golemina na listata
    override fun getItemCount(): Int {
       return moviesList.size
    }

    //bind na data i cela logika koja sto ja imame vo adapterot
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var myViewHolder = holder as MyViewHolder
        myViewHolder.bindData(moviesList[position],position)
    }

    inner class MyViewHolder (view: View):RecyclerView.ViewHolder(view){
        fun bindData(itemModel:MovieResult,position: Int){
            // preku pikaso go postavuvame vo ImageView posterot od filmot koj e na momentalnata pozicija vo recyclerView
            Picasso.get().load("http://image.tmdb.org/t/p/w185" + itemModel.poster_path).fit().into(itemView.movie_image_view)

            //inicijalizirame varijabla rating
            var rating = 0.0

            //proverka dali voteAverage ne e null za da pristapime kon postavuvanje slika preku piccaso vo ImageView zavisno od ratingot na filmot
            if (itemModel.vote_average != null){
               rating = itemModel.vote_average.toDouble()

               if (rating < 4){
                 Picasso.get().load(R.drawable.rate_02_hdpi).fit().into(itemView.rating_image_view)
               }else if (rating >= 4 && rating < 6){
                   Picasso.get().load(R.drawable.rate_04_hdpi).fit().into(itemView.rating_image_view)
               }else if (rating >= 6 && rating < 8){
                   Picasso.get().load(R.drawable.rate_06_hdpi).fit().into(itemView.rating_image_view)
               }else {
                   Picasso.get().load(R.drawable.rate_08_hdpi).fit().into(itemView.rating_image_view)
               }

                //go setirame so text dobieniot rating
                itemView.text_view_rating.setText(itemModel.vote_average + "/10")
            }

            //setirame text add to watchlist koj momentalno nema nikakva funkcija
            itemView.text_view_watchlist.setText("Add to watchlist")

            //setirame title od filmot koj e na momentalnata pozicija vo recyclerView
            itemView.text_view_title.text = itemModel.title

            //setirame na glavniot layout od movie_list_item.xml onClick event kade sto go izvestuvame moviesFragmentot za kliknatiot film
            itemView.main_layout.setOnClickListener(View.OnClickListener {
                moviesListOnClickEvent.invoke(MoviesOnClickEvent.onMovieClicked(itemModel))
            })

        }
    }
}