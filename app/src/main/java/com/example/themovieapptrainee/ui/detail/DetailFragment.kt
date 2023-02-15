package com.example.themovieapptrainee.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapptrainee.MainViewModel
import com.example.themovieapptrainee.databinding.FragmentDetailBinding
import com.example.themovieapptrainee.model.MovieEntity

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
        val item = mainActivityViewModel.generateItems().filter { it.id == filmId }.first()
        setupView(item)
    }

    private fun setupView(item: MovieEntity) {
        with(binding) {
            filmTitle.text = item.title
            filmYear.text = item.year
            filmRating.text = item.rating
            filmDescription.text = item.description
            filmImage.setImageURI(item.imageUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
