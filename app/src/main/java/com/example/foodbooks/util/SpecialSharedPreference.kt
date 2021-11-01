package com.example.foodbooks.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class SpecialSharedPreference {

    companion object{
        private var  sharedPreference: SharedPreferences?=null
        private val TIME="time"
        @Volatile private var instance :SpecialSharedPreference?=null

        private val lock=Any()
        operator fun invoke(context: Context) : SpecialSharedPreference = instance?: synchronized(lock){
            instance?:specialSharedPreferenceDoIt(context).also {
                instance=it
            }
        }

        private fun specialSharedPreferenceDoIt(context:Context) : SpecialSharedPreference{
            sharedPreference=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreference()
            //9.7 8.44
        }
    }

    fun saveTime(time:Long){
         sharedPreference?.edit(commit = true){
            putLong(TIME,time)
        }
    }
    fun takeTime()= sharedPreference?.getLong(TIME,0)

}