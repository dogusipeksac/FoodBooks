package com.example.foodbooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food

class FoodListViewModel : ViewModel(){

    val foods= MutableLiveData<List<Food>>()
    val foodErrorMessage=MutableLiveData<Boolean>()
    val foodLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val banana=Food("banana","10","20","30","40","www.test.com")
        val pear=Food("pear","101","210","320","140","www.test.com")
        val apple=Food("apple","120","220","310","240","www.test.com")

        val foodLists= arrayListOf<Food>(banana,pear,apple)
        foods.value=foodLists
        foodErrorMessage.value=false
        foodLoading.value=false
    }
}