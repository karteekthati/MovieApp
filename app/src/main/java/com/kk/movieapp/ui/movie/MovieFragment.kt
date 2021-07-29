package com.kk.movieapp.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kk.movieapp.ViewModel.MovieViewModel
import com.kk.movieapp.databinding.FragmentMovieBinding
import com.kk.movieapp.ui.Paging.MoviePagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    lateinit var binding: FragmentMovieBinding
    val viewModel: MovieViewModel by viewModels()
    val movieAdapter = MoviePagingAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return  binding.root
        //return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        binding.movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               query?.let {
                   viewModel.setQuery(it)
               }
               return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              return false
            }

        })

        movieAdapter.onMovieClick {
            val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        viewModel.list.observe(viewLifecycleOwner){

            binding.imgNodata.visibility = View.VISIBLE
            movieAdapter.submitData(lifecycle,it)
        }
    }

    private fun setRecyclerView() {
        binding.movieRecyclerview.apply {
            adapter =  movieAdapter
            layoutManager =  GridLayoutManager(requireContext(), 2)
        }

    }

}