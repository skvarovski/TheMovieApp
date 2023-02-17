package com.example.themovieapptrainee

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.example.themovieapptrainee.model.MovieEntity

class MainViewModel(var context: Application) : AndroidViewModel(context) {

    val filmList = mutableListOf<MovieEntity>()

    fun generateItems(): List<MovieEntity> {
        filmList.clear()
        filmList.add(MovieEntity(1, "Звёздные войны 1", "1990", "8.5", getRawFolderPath(R.raw.sw1), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, текст первой части"))
        filmList.add(MovieEntity(2, "Звёздные войны 2", "1980", "8.1", getRawFolderPath(R.raw.sw2), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, текст второй части"))
        filmList.add(MovieEntity(3, "Звёздные войны 3", "1995", "8.2", getRawFolderPath(R.raw.sw3), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, а это текст третьей части"))
        filmList.add(MovieEntity(4, "Звёздные войны 4", "2001", "8.7", getRawFolderPath(R.raw.sw4), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, да как так-то , это текст четвертой части"))
        filmList.add(MovieEntity(5, "Звёздные войны 5", "2005", "9.5", getRawFolderPath(R.raw.sw5), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, не может быть , но это уже пятая часть"))
        filmList.add(MovieEntity(6, "Звёздные войны 6", "2008", "6.5", getRawFolderPath(R.raw.sw6), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, мы на этом не останавливаемся, это уже шестая серия"))
        filmList.add(MovieEntity(7, "Звёздные войны 7", "2015", "4.5", getRawFolderPath(R.raw.sw7), "В далёкой-далёкой галактике, когда еще не было световых мечей жил был Оби Ван-Киноби, надеюсь мы закончили, это последняя седьмая серия"))
        return filmList
    }

    fun getRawFolderPath(image: Int): Uri {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + image) // "/raw/sw1.jpg")
    }
}
