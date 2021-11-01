package com.example.foodbooks.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbooks.R
import com.example.foodbooks.adapter.FoodAdapter
import com.example.foodbooks.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*


class FoodListFragment : Fragment() {


    private lateinit var viewModel:FoodListViewModel
    private val recyclerViewFoodAdapter=FoodAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()
        food_listRecyclerView.layoutManager=LinearLayoutManager(context)
        food_listRecyclerView.adapter=recyclerViewFoodAdapter
        food_listSwipeRefreshLayout.setOnRefreshListener {
            food_listProgressBar.visibility=View.VISIBLE
            food_failed_messageTextView.visibility=View.GONE
            food_listRecyclerView.visibility=View.GONE
            viewModel.refreshFromInternet()
            food_listSwipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer { foods ->
            foods?.let {
                food_listRecyclerView.visibility=View.VISIBLE
                recyclerViewFoodAdapter.setFoodList(foods)
            }
        })
        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer {errorMessage->
            errorMessage?.let {
                if(it){
                    food_failed_messageTextView.visibility=View.VISIBLE
                    food_listRecyclerView.visibility=View.GONE
                }else{
                    food_failed_messageTextView.visibility=View.GONE
                }
            }
        })
        viewModel.foodLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(it){
                    food_listRecyclerView.visibility=View.GONE
                    food_listProgressBar.visibility=View.VISIBLE
                    food_failed_messageTextView.visibility=View.GONE
                }else{
                    food_listProgressBar.visibility=View.GONE
                }
            }

        })
    }


}