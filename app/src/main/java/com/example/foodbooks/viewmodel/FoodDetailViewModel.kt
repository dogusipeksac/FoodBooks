package com.example.foodbooks.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food
import com.example.foodbooks.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) :BaseViewModel(application) {
    val foodLiveData=MutableLiveData<Food>()

    fun roomDataTake(uuid:Int){
        launch {
            val dao=FoodDatabase(getApplication()).foodDao()
            val food=dao.getFood(uuid)
            foodLiveData.value=food
        }
    }
}