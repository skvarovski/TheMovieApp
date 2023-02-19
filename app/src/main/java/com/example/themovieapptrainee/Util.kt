package com.example.themovieapptrainee

import android.content.Context
import android.net.Uri

fun Context.readFileFromAssets(filePath: String): String {
    return resources.assets.open(filePath).bufferedReader().use {
        it.readText()
    }
}

fun Context.createUriFromAssets(filePath: String): Uri {
    return Uri.parse("android.resource://$packageName/$filePath")
}
