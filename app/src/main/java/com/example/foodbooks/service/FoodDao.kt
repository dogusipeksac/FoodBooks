package com.example.foodbooks.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodbooks.model.Food

@Dao
interface FoodDao {
    //data access object
    @Insert
    suspend fun insertAll(vararg food:Food) : List<Long>

    // Insert-> Room ,insert into
    // suspend-> coroutine scope
    // vararg -> birden fazla ve istediğimiz sayıda besin
    // List<Long> long , id

    @Query("select * from Food")
    suspend fun getAllFood() : List<Food>



    @Query("select * from Food where uuid= :foodId")
    suspend fun getFood(foodId:Int):Food

    @Query("delete from Food")
    suspend fun deleteAllFood()

    @Query("delete from Food where uuid=:foodId")
    suspend fun deleteFood(foodId:Int):Food

}