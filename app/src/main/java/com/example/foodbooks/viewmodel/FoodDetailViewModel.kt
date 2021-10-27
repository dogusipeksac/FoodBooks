package com.example.foodbooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food

class FoodDetailViewModel :ViewModel() {
    val foodLiveData=MutableLiveData<Food>()

    fun roomDataTake(){
        val banana=Food("banana","10","20","30","40","www.test.com")
        foodLiveData.value=banana
    }
}