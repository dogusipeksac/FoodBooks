package com.example.foodbooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbooks.R
import com.example.foodbooks.databinding.ItemFoodBinding
import com.example.foodbooks.model.Food
import com.example.foodbooks.util.downloadImage
import com.example.foodbooks.util.placeHolderDoIt
import com.example.foodbooks.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter(val foodList:ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() ,FoodClickListener {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.item_food,parent,false)
        val view=DataBindingUtil.inflate<ItemFoodBinding>(inflater,R.layout.item_food,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.food=foodList[position]
        holder.view.listener=this


        /*
        holder.itemView.food_name_textview.text=foodList[position].food_name
        holder.itemView.food_calorie_textview.text=foodList[position].calorie
        holder.itemView.setOnClickListener{
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.food_imageview.downloadImage(
            foodList[position].imageUrl,
            placeHolderDoIt(holder.itemView.context))

         */
    }


    override fun getItemCount(): Int {
        return foodList.size
    }

    class ViewHolder(var view : ItemFoodBinding) : RecyclerView.ViewHolder(view.root){


        
    }
    fun setFoodList(newFoodList:List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()

    }

    override fun foodClicked(view: View) {
        val uuid=view.food_uud.text.toString().toIntOrNull()
        uuid?.let {
            val action=FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }


}