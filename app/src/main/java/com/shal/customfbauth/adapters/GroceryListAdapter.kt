package com.shal.customfbauth.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shal.customfbauth.R
import com.shal.customfbauth.fragments.HomeFragment
import com.shal.customfbauth.models.Grocery
import kotlinx.android.synthetic.main.grocery_row_item.view.*


class GroceryListAdapter(private val homeFragment: HomeFragment) :
    RecyclerView.Adapter<GroceryListAdapter.ViewHolder>() {

    var groceryList: List<Grocery> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos = 0

        fun setData(name: String, quantity: Int, position: Int) {
            itemView.txtItemName.text = name
            itemView.txtQuantity.text = quantity.toString()
            itemView.checkStatus.isChecked = false
            pos = position
        }

        init {
            itemView.checkStatus.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val updatedGrocery = groceryList[pos]
                    updatedGrocery.cart = true
                    notifyItemRemoved(pos)
                    homeFragment.updateCart(updatedGrocery)
                }
            }
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grocery = groceryList[position]
        holder.setData(grocery.name!!, grocery.quantity!!, position)

    }

    override fun getItemCount() = groceryList.size

    fun setGroceries(groceries: List<Grocery>) {
        groceryList = groceries
        notifyDataSetChanged()
    }

}