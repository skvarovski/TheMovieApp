package com.example.themovieapptrainee.data

import android.content.Context

class Repository {


    private fun loadJsonFromAssets(context: Context): String {
        var result = ""

        runCatching {
            val input = context.assets.open("films.json")
            val size = input.available()
            val bytes = ByteArray(size)
            input.read(bytes)
            input.close()
            result = String(bytes)
        }.onFailure {
            it.printStackTrace()
        }

        return result
    }

}