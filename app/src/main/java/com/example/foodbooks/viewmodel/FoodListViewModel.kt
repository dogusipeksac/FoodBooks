package com.example.foodbooks.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food
import com.example.foodbooks.service.FoodApiService
import com.example.foodbooks.service.FoodDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application){

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
                        sqLiteHide(t)
                    }

                    override fun onError(e: Throwable) {
                        foodErrorMessage.value=true
                        foodLoading.value=false
                        e.printStackTrace()
                    }

                })

        )
    }
    private fun showFood(foodList:List<Food>){
        foods.value=foodList
        foodErrorMessage.value=false
        foodLoading.value=false
    }

    private fun sqLiteHide(foodList:List<Food>){
        launch {
            val dao=FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList=dao.insertAll(*foodList.toTypedArray())
            var i=0
            //besinlere room üzerinden rastgele atılan idleri
            // sıralı şekilde bizim listemize ekleeme
            while (i<foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i++
            }
            showFood(foodList)
        }
    }
}