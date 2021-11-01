package com.example.foodbooks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbooks.model.Food
import com.example.foodbooks.service.FoodApiService
import com.example.foodbooks.service.FoodDatabase
import com.example.foodbooks.util.SpecialSharedPreference
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

    //nanotime from     minutes
    private var updateTime= 10 * 60 * 1000 *1000 * 1000L

    private val specialSharedPreference=SpecialSharedPreference(getApplication())


    fun refreshData(){
        val savedTime=specialSharedPreference.takeTime()
        println("savedTime $savedTime")
        println("state ${System.nanoTime() - savedTime!! < updateTime}")
        if(savedTime!=null &&
            savedTime!=0L &&
            System.nanoTime() - savedTime < updateTime){
            //sqlitedan al
            dataGetFromSqLite()

        }
        else{
            //10 dk fazla geçmiştir
            dataGetFromInternet()
        }


    }
    fun refreshFromInternet(){
        dataGetFromInternet()
    }
    private fun dataGetFromSqLite(){
        foodLoading.value=true
        launch {
            val foodList=FoodDatabase(getApplication()).foodDao().getAllFood()
            showFood(foodList)
            Toast.makeText(getApplication(),"besinleri Room'dan aldık",Toast.LENGTH_SHORT).show()
        }
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
                        Toast.makeText(getApplication(),"besinleri İnternetten aldık",Toast.LENGTH_SHORT).show()
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
        specialSharedPreference.saveTime(System.nanoTime())
    }
}