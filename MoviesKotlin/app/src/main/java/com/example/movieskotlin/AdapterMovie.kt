package com.example.movieskotlin

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieskotlin.databinding.ItemMoiveBinding
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.coroutineContext

class  AdapterMovie (var onClick:(Int)->Unit ) : RecyclerView.Adapter<AdapterMovie.addMovie>() {

    lateinit var list:ArrayList<MovieResult>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): addMovie {
        val binding:ItemMoiveBinding=ItemMoiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return addMovie(binding)
    }

    override fun getItemCount(): Int {
    return list?.size?:0
    }

    override fun onBindViewHolder(holder: addMovie, position: Int) {
       val  movieResult =list?.get(position)!!
       holder.bind(movieResult)
        holder.itemView.setOnClickListener{

        }
    }
  inner  class  addMovie  (val binding: ItemMoiveBinding ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
            onClick.invoke(list.get(layoutPosition).id)
            }
        }
        fun bind(movieResult: MovieResult){
            binding.textTitle.text=movieResult?.title
            Glide.with(binding.root.context).load("https://image.tmdb.org/t/p/w500/"+movieResult?.backdrop_path).into(binding.imageMovie)
        }

    }

}