package com.joaoflaviofreitas.inchurch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.joaoflaviofreitas.inchurch.databinding.FragmentHomeBinding
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.utils.RecyclerViewLayouts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var trendingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var searchAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setAdapters()
        setupStatesOfRvs()
        searchMovieByTerm()
        observeResponse()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            retryAdapters()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setAdapters() {
        trendingAdapter = MovieAdapter(RecyclerViewLayouts.HORIZONTAL_LAYOUT) {
            navigateToDetailsFragment(it)
        }
        binding.rvTrendingMovies.adapter = trendingAdapter
        popularAdapter = MovieAdapter(RecyclerViewLayouts.HORIZONTAL_LAYOUT) {
            navigateToDetailsFragment(it)
        }
        binding.rvPopularMovies.adapter = popularAdapter
        upcomingAdapter = MovieAdapter(RecyclerViewLayouts.HORIZONTAL_LAYOUT) {
            navigateToDetailsFragment(it)
        }
        binding.rvUpcomingMovies.adapter = upcomingAdapter
        searchAdapter =
            MovieAdapter(RecyclerViewLayouts.GRID_LAYOUT) { navigateToDetailsFragment(it) }
        binding.rvSearchMovie.adapter = searchAdapter
        binding.rvSearchMovie.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeResponse() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.trendingMovies.collectLatest { pagingData ->
                        trendingAdapter.submitData(pagingData)
                    }
                }
                launch {
                    viewModel.popularMovies.collectLatest { pagingData ->
                        popularAdapter.submitData(pagingData)
                    }
                }
                launch {
                    viewModel.upcomingMovies.collectLatest { pagingData ->
                        upcomingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun searchMovieByTerm() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank() && newText.isNotEmpty()) {
                    dismissTheMainRecyclerViews()
                    binding.rvSearchMovie.isVisible = true
                    observeSearch(newText)
                } else {
                    binding.rvSearchMovie.isGone = true
                    admitsTheMainRecyclerViews()
                }
                return false
            }
        })
    }

    private fun observeSearch(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchMoviesByTerm(query).collectLatest { pagingData ->
                    searchAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
    }

    private fun dismissTheMainRecyclerViews() {
        binding.rvTrendingMovies.isGone = true
        binding.rvUpcomingMovies.isGone = true
        binding.rvPopularMovies.isGone = true
        binding.trendingMoviesTitle.isGone = true
        binding.popularMoviesTitle.isGone = true
        binding.upcomingMoviesTitle.isGone = true
        binding.rvSearchMovie.isVisible = true
    }

    private fun admitsTheMainRecyclerViews() {
        binding.rvTrendingMovies.isVisible = true
        binding.rvUpcomingMovies.isVisible = true
        binding.rvPopularMovies.isVisible = true
        binding.trendingMoviesTitle.isVisible = true
        binding.popularMoviesTitle.isVisible = true
        binding.upcomingMoviesTitle.isVisible = true
        binding.rvSearchMovie.isGone = true
    }

    private fun setupStatesOfRvs() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                trendingAdapter.loadStateFlow.collectLatest { loadStates ->
                    binding.rvSearchMovie.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query != null
                    binding.rvTrendingMovies.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                    binding.trendingMoviesTitle.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && loadStates.source.refresh !is LoadState.Loading
                    binding.rvPopularMovies.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                    binding.popularMoviesTitle.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && loadStates.source.refresh !is LoadState.Loading
                    binding.rvUpcomingMovies.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                    binding.upcomingMoviesTitle.isVisible =
                        loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && loadStates.source.refresh !is LoadState.Loading
                    handleError(loadStates)
                    binding.loadBar.isVisible = loadStates.source.refresh is LoadState.Loading
                    binding.errorMessage.isVisible = loadStates.source.refresh is LoadState.Error
                }
            }
        }
    }

    private fun handleError(loadStates: CombinedLoadStates) {
        val errorState = loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(requireContext(), "No Network", Toast.LENGTH_LONG).show()
        }
    }

    private fun retryAdapters() {
        searchAdapter.retry()
        trendingAdapter.retry()
        popularAdapter.retry()
        upcomingAdapter.retry()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetailsFragment(movie: Movie) {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetails(movie.id)
        findNavController().navigate(action)
    }
}
