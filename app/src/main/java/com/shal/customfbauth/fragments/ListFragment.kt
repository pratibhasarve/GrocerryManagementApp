package com.shal.customfbauth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.shal.customfbauth.R
import com.shal.customfbauth.activities.CreateItem
import com.shal.customfbauth.activities.DashboardActivity
import com.shal.customfbauth.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*


class ListFragment : Fragment() {

    val TAG = "ListFragment"

    private val tabIcons = intArrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_bill_check_24,
        R.drawable.ic_baseline_event_note_24
    )
    var tab_layout: TabLayout? = null

    val pageHistory = Stack<Int>()
    var saveToHistory = false
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        viewPager = root.findViewById(R.id.viewPager)
        val viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager, 1)

        // add the fragments
        viewPagerAdapter.add(HomeFragment(), "Home")
        viewPagerAdapter.add(BillFragment(), "Bill")
        viewPagerAdapter.add(NotesFragment(), "Notes")
        tab_layout = root.findViewById(R.id.tab_layout)

        viewPager?.adapter = viewPagerAdapter
        root.tab_layout.setupWithViewPager(root.viewPager);
        setupTabIcons()


        pageHistory.push(0);
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (saveToHistory) {
                    if (pageHistory.contains(position)) {
                        pageHistory.remove(position)
                        pageHistory.push(position);
                    } else {
                        pageHistory.push(position);
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        saveToHistory = true;

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            if (pageHistory.size > 1) {

                saveToHistory = false;
                pageHistory.pop()
                viewPager?.currentItem = pageHistory.peek()
                saveToHistory = true;

            } else {

                if(pageHistory.size ==1){
                    pageHistory.pop()
                }
                if (viewPager?.currentItem == 0){
                    requireActivity().finish()
                }else{
                    viewPager?.currentItem = 0
                }
            }

        }

        root.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(requireActivity(), CreateItem::class.java)
            startActivityForResult(intent, DashboardActivity.ACTIVITY_RESULT_REQUEST)
        }

        return root
    }

    private fun setupTabIcons() {
        tab_layout?.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout?.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout?.getTabAt(2)?.setIcon(tabIcons[2])
    }
}