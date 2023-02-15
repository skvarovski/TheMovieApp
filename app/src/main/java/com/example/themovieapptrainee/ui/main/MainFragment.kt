package com.example.themovieapptrainee.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapptrainee.R
import com.example.themovieapptrainee.adapter.FilmAdapter
import com.example.themovieapptrainee.databinding.FragmentMainBinding
import com.example.themovieapptrainee.model.MovieEntity

class MainFragment : Fragment(), FilmAdapter.CallbackListener {

    lateinit var filmAdapter: FilmAdapter
    private lateinit var mainActivityViewModel: com.example.themovieapptrainee.MainViewModel
    private var _binding: FragmentMainBinding? = null
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding = _binding as FragmentMainBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel = requireActivity().let {
            ViewModelProvider(it)[com.example.themovieapptrainee.MainViewModel::class.java]
        }
        filmAdapter = FilmAdapter()
        setupView()
    }
    private fun setupView() {
        // orderAdapter.initCallbackListener(this)
        binding.rvAdapter.adapter = filmAdapter
        filmAdapter.initListener(this)
        binding.rvAdapter.layoutManager = LinearLayoutManager(context)

        filmAdapter.items = mainActivityViewModel.generateItems()
    }

    override fun onClickItem(item: MovieEntity) {
        Toast.makeText(activity, "click on ${item.description}", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply {
            putInt("film_id", item.id)
        }
        findNavController().navigate(
            R.id.action_mainFragment_to_detailFragment,
            bundle,
            navOptions { // Use the Kotlin DSL for building NavOptions
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            },
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
