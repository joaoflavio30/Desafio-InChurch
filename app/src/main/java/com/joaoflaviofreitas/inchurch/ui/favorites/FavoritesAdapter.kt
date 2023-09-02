package com.joaoflaviofreitas.inchurch.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.joaoflaviofreitas.inchurch.BuildConfig
import com.joaoflaviofreitas.inchurch.databinding.GridMovieItemBinding
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.utils.displayDateFormat

class FavoritesAdapter(private val clickItem: (Movie) -> Unit) : ListAdapter<Movie, FavoritesAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: GridMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder =
        ViewHolder(GridMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
