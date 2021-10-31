package com.example.foodbooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food
import com.example.foodbooks.service.FoodApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FoodListViewModel : ViewModel(){

    val foods= MutableLiveData<List<Food>>()
    val foodErrorMessage=MutableLiveData<Boolean>()
    val foodLoading=MutableLiveData<Boolean>()

    private val foodApiService=FoodApiService()
    private val disposable=CompositeDisposable()


    fun refreshData(){
        dataGetFromInternet()
    }
    private fun dataGetFromInternet(){
        foodLoading.value=true

        //Input output default ui thread
        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        foods.value=t
                        foodErrorMessage.value=false
                        foodLoading.value=false
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value=true
                        foodLoading.value=false
                        e.printStackTrace()
                    }

                })

        )
    }

}