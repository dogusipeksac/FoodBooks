package com.example.foodbooks.model

import com.google.gson.annotations.SerializedName

data class Food (
    @SerializedName("isim")
    val food_name:String?,
    @SerializedName("kalori")
    val calorie:String?,
    @SerializedName("karbonhidrat")
    val carbohydrate:String?,
    @SerializedName("protein")
    val protein:String?,
    @SerializedName("yag")
    val oil:String?,
    @SerializedName("gorsel")
    val imageUrl:String?){
}