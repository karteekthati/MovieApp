package com.kk.movieapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kk.movieapp.R
import com.kk.movieapp.ViewModel.MovieViewModel
import com.kk.movieapp.databinding.FragmentDetailsBinding
import com.kk.movieapp.remote.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    lateinit var binding:FragmentDetailsBinding
    val  args: DetailsFragmentArgs by navArgs()
    val viewModel:MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
        args.imdbId?.let { viewModel.getMovieDetails(it) }

        viewModel.movieDetails.observe(viewLifecycleOwner){
            when(it.getContentIfNotHandled()?.status){
                Status.LOADING->{
                    binding.detailsProgressbar.visibility = View.VISIBLE
                }
                Status.ERROR->{
                    binding.detailsProgressbar.visibility = View.GONE
                }
                Status.SUCCESS->{
                    binding.detailsProgressbar.visibility = View.GONE
                    binding.movieDetails = it.peekContent().data
                }
            }
        }
    }

}