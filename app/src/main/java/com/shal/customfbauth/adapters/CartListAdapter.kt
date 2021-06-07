package com.shal.customfbauth.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shal.customfbauth.activities.DashboardActivity
import com.shal.customfbauth.R
import com.shal.customfbauth.activities.CartActivity
import com.shal.customfbauth.models.Grocery
import kotlinx.android.synthetic.main.cart_row_item.view.*
import kotlinx.android.synthetic.main.grocery_row_item.view.*
import kotlinx.android.synthetic.main.grocery_row_item.view.txtItemName
import kotlinx.android.synthetic.main.grocery_row_item.view.txtQuantity

class CartListAdapter(private val activity: CartActivity) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    var groceryList: List<Grocery> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos = 0

        fun setData(name: String, quantity: Int, position: Int) {
            itemView.txtItemName.text = name
            itemView.txtQuantity.text = quantity.toString()
            pos = position
        }

        init {
            itemView.btnCancelItem.setOnClickListener{
                val updatedGrocery = groceryList[pos]
                updatedGrocery.cart = false
                notifyItemRemoved(pos)
                activity.updateCart(updatedGrocery)
            }
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_row_item, parent, false)

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