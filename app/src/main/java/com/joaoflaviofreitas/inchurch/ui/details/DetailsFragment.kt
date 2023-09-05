package com.joaoflaviofreitas.inchurch.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.joaoflaviofreitas.inchurch.BuildConfig
import com.joaoflaviofreitas.inchurch.R
import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.databinding.FragmentDetailsBinding
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.utils.displayDateFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var genreAdapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeFavorite()
        getMovieDetails()
        observeViewModel()
        setGenreView()
        initClickListeners()
        return root
    }

    private fun initClickListeners() {
        binding.starBtn.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collectLatest {
                        if (it is Response.Success && it.data != null) {
                            viewModel.addOrRemoveFavoriteMovie(FavoriteMovieId(args.movieId, it.data.title), it.data)
                        }
                    }
                }
            }
        }
    }

    private fun observeFavorite() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteMovie.collectLatest {
                    handleIsFavoriteMovie(it)
                }
            }
        }
    }

    private fun observeViewModel() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteMovie.collectLatest {
                    handleIsFavoriteMovie(it)
                }
            }
        }
    }

    private fun handleIsFavoriteMovie(isFavorite: Boolean) {
        if (isFavorite) {
            binding.starBtn.setImageResource(R.drawable.baseline_star_24)
        } else {
            binding.starBtn.setImageResource(R.drawable.baseline_star_border_24)
        }
    }

    private fun setGenreView() {
        genreAdapter = DetailsAdapter()
        binding.rvGenreMovie.adapter = genreAdapter
    }

    private fun setupUi(movie: Movie) {
        with(binding) {
            Glide.with(requireContext())
                .load("${BuildConfig.BASE_IMAGE_URL_ORIGINAL}${movie.posterPath}")
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL,
                ).placeholder(R.drawable.gradient_background).transition(
                    DrawableTransitionOptions.withCrossFade(),
                ).into(binding.movieImage)
            handleIsFavoriteMovie(movie.isFavorite)
            backBtn.setOnClickListener {
                navigateToHomeFragment()
            }
            circleBar.max = 10
            circleBar.setProgress(movie.voteAverage.toInt(), true)
            scoreMovie.text = movie.voteAverage.toString()
            movieTitle.text = movie.title
            releaseDate.text = displayDateFormat.format(movie.releaseDate)
            movieDescription.text = movie.overview
        }
    }

    private fun getMovieDetails() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMovieDetails(args.movieId)
                }
                launch {
                    viewModel.uiState.collectLatest {
                        when (it) {
                            is Response.Success -> {
                                binding.loadBar.isGone = true
                                if (it.data != null) {
                                    setupUi(it.data)
                                    setGenresOfMovie(it.data)
                                }
                            }

                            is Response.Loading -> binding.loadBar.isVisible = true
                            is Response.Error -> {
                                binding.loadBar.isGone = true
                                binding.errorText.isVisible = true
                                binding.errorText.text = it.errorMessage
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGenresOfMovie(movie: Movie) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genres.collectLatest {
                    val genresForMovie = movie.genres
                    genreAdapter.submitList(genresForMovie)
                    Log.d("genres", "$it")
                }
            }
        }
    }

    private fun navigateToHomeFragment() {
        findNavController().popBackStack()
    }
}
