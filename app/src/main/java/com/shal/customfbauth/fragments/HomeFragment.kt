package com.shal.customfbauth.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shal.customfbauth.R
import com.shal.customfbauth.adapters.GroceryListAdapter
import com.shal.customfbauth.models.Grocery
import com.shal.customfbauth.viewmodels.GroceryViewModel
import com.shal.customfbauth.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val model: GroceryViewModel by viewModels()
    private var adapter: GroceryListAdapter? = null
    private lateinit var groceryList: RecyclerView
    //var list: List<Grocery> = mutableListOf()
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        groceryList = root.findViewById(R.id.groceryList)

        adapter = GroceryListAdapter(this)
        setAdapter()
        getGroceries(root)

        root.swipeRefrshLayout.setOnRefreshListener {
            Log.i("TAG", "Refresh Grocery list")
            model.getGroceryFromFirebase()
        }
        return root
    }


    private fun getGroceries(root: View) {
        model.groceries.observe(viewLifecycleOwner, Observer { groceries ->
            Log.i("TAG", " Coroutine groceries size ${groceries.size}")
            root.swipeRefrshLayout.isRefreshing = false
            groceries?.let {
                if (groceries.isEmpty()) {
                    model.getGroceryFromFirebase()
                }
                adapter!!.setGroceries(groceries)
            }
        })

    }

    private fun setAdapter() {
        groceryList.adapter = adapter
        groceryList.layoutManager = LinearLayoutManager(activity)
    }

    fun updateCart(grocery: Grocery) {
        model.updateCart(grocery)
    }
}