package com.example.foodbooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbooks.R
import com.example.foodbooks.model.Food
import com.example.foodbooks.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter(val foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_food,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.food_name_textview.text=foodList[position].food_name
        holder.itemView.food_calorie_textview.text=foodList[position].calorie
        holder.itemView.setOnClickListener{
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(3)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }
    fun setFoodList(newFoodList:List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()

    }


}