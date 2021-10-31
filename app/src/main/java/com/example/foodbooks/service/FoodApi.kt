package com.example.foodbooks.service

import com.example.foodbooks.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodApi {
    // get , post
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    //base url -> https://raw.githubusercontent.com
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFood() : Single<List<Food>>

}