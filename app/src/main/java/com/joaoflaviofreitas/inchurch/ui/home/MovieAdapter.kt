package com.joaoflaviofreitas.inchurch.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.joaoflaviofreitas.inchurch.BuildConfig
import com.joaoflaviofreitas.inchurch.databinding.GridMovieItemBinding
import com.joaoflaviofreitas.inchurch.databinding.MovieItemBinding
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.utils.RecyclerViewLayouts
import com.joaoflaviofreitas.inchurch.utils.displayDateFormat

class MovieAdapter(
    private val layout: RecyclerViewLayouts,
    private val clickItem: (Movie) -> Unit,
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {

    inner class MainViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieDateRelease.text = displayDateFormat.format(movie.releaseDate)
            binding.scoreMovie.text = movie.voteAverage.toString()
            binding.circleBar.max = 10
            binding.circleBar.setProgress(movie.voteAverage.toInt(), true)
            Glide.with(binding.root.context)
                .load("${BuildConfig.BASE_IMAGE_URL}${movie.posterPath}").diskCacheStrategy(
                    DiskCacheStrategy.ALL,
                ).transition(
                    DrawableTransitionOptions.withCrossFade(),
                ).into(binding.movieImage)
            binding.root.setOnClickListener {
                clickItem(movie)
            }
        }
    }

    inner class GridViewHolder(private val binding: GridMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieDateRelease.text = displayDateFormat.format(movie.releaseDate)
            binding.scoreMovie.text = movie.voteAverage.toString()
            binding.circleBar.max = 10
            binding.circleBar.setProgress(movie.voteAverage.toInt(), true)
            Glide.with(binding.root.context)
                .load("${BuildConfig.BASE_IMAGE_URL}${movie.posterPath}").transition(
                    DrawableTransitionOptions.withCrossFade(),
                ).into(binding.movieImage)
            binding.root.setOnClickListener {
                clickItem(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (layout) {
            RecyclerViewLayouts.HORIZONTAL_LAYOUT -> {
                val v1 = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MainViewHolder(v1)
            }

            RecyclerViewLayouts.GRID_LAYOUT -> {
                val v2 = GridMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(v2)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is MainViewHolder -> {
                    holder.bind(item)
                }
                is GridViewHolder -> {
                    holder.bind(item)
                }
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
