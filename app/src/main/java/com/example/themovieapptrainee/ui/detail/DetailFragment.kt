package com.example.themovieapptrainee.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.themovieapptrainee.MainViewModel
import com.example.themovieapptrainee.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    lateinit var binding: FragmentDetailBinding
    private var filmId: Int? = null
    lateinit var mainActivityViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = _binding as FragmentDetailBinding
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmId = arguments?.getInt("film_id")
        mainActivityViewModel = requireActivity().let {
            ViewModelProvider(it)[com.example.themovieapptrainee.MainViewModel::class.java]
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.movieItems.collect { items ->
                    if (items.isNotEmpty()) {
                        Log.d("TEST", "observer details...")
                        run {
                            val item = items.first { it.id == filmId }
                            with(binding) {
                                filmTitle.text = item.title
                                //filmYear.text = item.year
                                filmRating.text = item.rating
                                filmDescription.text = item.description
                                context?.let { Glide.with(it).load(item.imageUrl).into(filmImage) }
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
