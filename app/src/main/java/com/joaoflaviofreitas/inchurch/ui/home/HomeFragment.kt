package com.joaoflaviofreitas.inchurch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.joaoflaviofreitas.inchurch.utils.getQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        searchMovieByTerm()
        observeResponse()
        observeSearch()
        setupLoadStatesOfRvs()
        setupUiState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            retryAdapters()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            if (binding.searchView.query.isEmpty()) {
                admitsTheMainRecyclerViews()
                Log.d("teste5555", "${binding.searchView.query}")
            } else {
                dismissTheMainRecyclerViews()
                Log.d("teste5555", "${binding.searchView.query}")
                viewModel.searchMoviesByTerm(binding.searchView.query.toString())
            }
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

    @OptIn(FlowPreview::class)
    private fun searchMovieByTerm() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.searchView.getQueryTextChangeStateFlow().debounce(300)
                    .distinctUntilChanged().collectLatest {
                        if (!it.isNullOrBlank() && it.isNotEmpty()) {
                            viewModel.searchMoviesByTerm(it)
                            withContext(Dispatchers.Main) {
                                dismissTheMainRecyclerViews()
                            }
                        } else {
                            if (binding.searchView.query.isEmpty()) {
                                withContext(Dispatchers.Main) {
                                    admitsTheMainRecyclerViews()
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun observeSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.searchedMovie.collectLatest { pagingData ->
                        searchAdapter.submitData(pagingData)
                    }
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
    private fun setupLoadStatesOfRvs() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    trendingAdapter.loadStateFlow.collectLatest { loadStates ->
                        binding.rvTrendingMovies.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                        binding.trendingMoviesTitle.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && trendingAdapter.itemCount != 0
                        binding.rvPopularMovies.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                        binding.popularMoviesTitle.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && trendingAdapter.itemCount != 0
                        binding.rvUpcomingMovies.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty()
                        binding.upcomingMoviesTitle.isVisible =
                            loadStates.source.refresh is LoadState.NotLoading && binding.searchView.query.isEmpty() && trendingAdapter.itemCount != 0
                        handleError(loadStates)
                        binding.loadBar.isVisible = loadStates.source.refresh is LoadState.Loading
                        binding.errorMessage.isVisible = loadStates.source.refresh is LoadState.Error
                    }
                }
                launch {
                    searchAdapter.loadStateFlow.collectLatest { loadStates ->
                        binding.loadBar.isVisible = loadStates.source.refresh is LoadState.Loading
                        handleError(loadStates)
                    }
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
