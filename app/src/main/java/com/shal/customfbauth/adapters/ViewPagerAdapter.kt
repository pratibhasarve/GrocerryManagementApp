package com.shal.customfbauth.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager, int: Int) : FragmentPagerAdapter(fm, int) {

    private val fragments = arrayListOf<Fragment>()
    private val fragmentTitle = arrayListOf<String>()

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }


    fun add(fragment: Fragment?, title: String?) {
        if (fragment != null) {
            fragments.add(fragment)
        }
        if (title != null) {
            fragmentTitle.add(title)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle[position]
    }

}