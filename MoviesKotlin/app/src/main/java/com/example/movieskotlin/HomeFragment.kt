package com.example.movieskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieskotlin.databinding.ActivityMainBinding
import com.example.movieskotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActvityViewModel: MainActvityViewModel
    lateinit var adapterMovie: AdapterMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActvityViewModel =ViewModelProvider(requireActivity()).get(MainActvityViewModel::class.java)
        binding= FragmentHomeBinding.inflate(LayoutInflater.from(parentFragment?.context))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view)
        adapterMovie= AdapterMovie {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(it))
        }
        mainActvityViewModel.getMovies()
        mainActvityViewModel.movieLiveData.observe(viewLifecycleOwner){
        adapterMovie.list= it?.results as ArrayList<MovieResult>
            binding.recyclerMovie.adapter=adapterMovie
        }

        binding.recyclerMovie.setOnClickListener{

        }
        binding.root
    }


}