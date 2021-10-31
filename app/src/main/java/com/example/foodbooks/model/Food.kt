package com.example.foodbooks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food (
    @ColumnInfo(name="isim")
    @SerializedName("isim")
    val food_name:String?,
    @ColumnInfo(name="kalori")
    @SerializedName("kalori")
    val calorie:String?,
    @ColumnInfo(name="karbonhidrat")
    @SerializedName("karbonhidrat")
    val carbohydrate:String?,
    @ColumnInfo(name="protein")
    @SerializedName("protein")
    val protein:String?,
    @ColumnInfo(name="yag")
    @SerializedName("yag")
    val oil:String?,
    @ColumnInfo(name="gorsel")
    @SerializedName("gorsel")
    val imageUrl:String?){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}