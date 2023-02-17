package com.example.themovieapptrainee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themovieapptrainee.adapter.FilmAdapter
import com.example.themovieapptrainee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FilmAdapter
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = MainViewModel(application)
        adapter = FilmAdapter()
        setContentView(binding.root)

        binding.rvAdapter.adapter = adapter
        val itemList = viewModel.generateItems()
        adapter.items = itemList
    }
}
