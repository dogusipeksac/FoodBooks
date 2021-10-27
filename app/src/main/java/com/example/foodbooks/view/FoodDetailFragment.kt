package com.example.foodbooks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.foodbooks.R
import com.example.foodbooks.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*


class FoodDetailFragment : Fragment() {
    private lateinit var viewModel:FoodDetailViewModel
    private var foodId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId=FoodDetailFragmentArgs.fromBundle(it).foodId
            println(foodId)
        }
        viewModel=ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.roomDataTake()
        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food->
            food?.let {
                food_detail_name_textview.text=it.food_name
                food_detail_calorie_textview.text=it.calorie
                food_detail_carbohydrate_textview.text=it.carbohydrate
                food_detail_protein_textview.text=it.protein
                food_detail_oil_textview.text=it.oil

            }

        })
    }

}