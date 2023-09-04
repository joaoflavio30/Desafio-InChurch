package com.joaoflaviofreitas.inchurch.ui.favorites

import android.os.Bundle
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
import androidx.recyclerview.widget.GridLayoutManager
import com.joaoflaviofreitas.inchurch.databinding.FragmentFavoritesBinding
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.utils.getQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupAdapter()
        searchMovieByTerm()
        observeFavoriteMovies()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            if (binding.searchView.query == null) {
                viewModel.fetchFavoritesMovies()
            } else {
                viewModel.searchFavoriteMovie(binding.searchView.query.toString())
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupAdapter() {
        favoritesAdapter = FavoritesAdapter { navigateToDetailsFragment(it) }
        binding.rvFavoriteMovie.adapter = favoritesAdapter
        binding.rvFavoriteMovie.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeFavoriteMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { response ->
                    when (response) {
                        is Response.Success -> {
                            setupUi(response.data)
                            binding.errorMessage.isGone = true
                        }

                        is Response.Error -> {
                            binding.errorMessage.isVisible = true
                            handleError(response.errorMessage)
                            binding.notFoundMovie.isVisible = false
                        }

                        is Response.Loading -> {
                            binding.errorMessage.isGone = true
                            handleLoading()
                            binding.notFoundMovie.isVisible = false
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchMovieByTerm() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.searchView.getQueryTextChangeStateFlow().debounce(300)
                    .distinctUntilChanged().collectLatest {
                        if (it != null) viewModel.searchFavoriteMovie(it)
                    }
            }
        }
    }
    private fun handleLoading() {
        binding.loadBar.isVisible = true
    }

    private fun handleError(errorMessage: String) {
        binding.loadBar.isGone = true
        binding.errorMessage.text = errorMessage
    }

    private fun setupUi(data: MutableList<Movie>) {
        binding.loadBar.isGone = true
        favoritesAdapter.submitList(data)
    }

    private fun navigateToDetailsFragment(movie: Movie) {
        val action =
            FavoritesFragmentDirections.actionNavigationFavoritesToNavigationDetails(movie.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
