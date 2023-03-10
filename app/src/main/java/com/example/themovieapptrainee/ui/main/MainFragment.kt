package com.example.themovieapptrainee.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovieapptrainee.R
import com.example.themovieapptrainee.adapter.FilmAdapter
import com.example.themovieapptrainee.databinding.FragmentMainNewBinding
import com.example.themovieapptrainee.model.TheMovieEntity
import kotlinx.coroutines.launch

class MainFragment : Fragment(), FilmAdapter.CallbackListener {

    lateinit var filmAdapter: FilmAdapter
    private lateinit var mainActivityViewModel: com.example.themovieapptrainee.MainViewModel
    private var _binding: FragmentMainNewBinding? = null
    lateinit var binding: FragmentMainNewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainNewBinding.inflate(inflater, container, false)
        binding = _binding as FragmentMainNewBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel = requireActivity().let {
            ViewModelProvider(it)[com.example.themovieapptrainee.MainViewModel::class.java]
        }
        filmAdapter = FilmAdapter()
        setupView()
        setupObserver()

        binding.textInputLayout.setEndIconOnClickListener {
            Toast.makeText(context, "click me", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupView() {
        // orderAdapter.initCallbackListener(this)
        binding.rvAdapter.adapter = filmAdapter
        filmAdapter.initListener(this)
        binding.rvAdapter.layoutManager = GridLayoutManager(context, 2)
        mainActivityViewModel.getMovieFromRepository()
    }

    fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.movieItems.collect { items ->
                    run {
                        filmAdapter.items = items
                        Log.d("TEST", "observer...")
                    }
                }
            }
        }
    }

    override fun onClickItem(item: TheMovieEntity) {
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
