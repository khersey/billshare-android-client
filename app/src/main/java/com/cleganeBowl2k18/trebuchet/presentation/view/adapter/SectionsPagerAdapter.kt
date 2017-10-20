package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by khersey on 2017-10-18.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragments = LinkedHashMap<Fragment, String>()

    override fun getPageTitle(position: Int): CharSequence {
        return fragments.values.elementAt(position)
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.put(fragment, title)
    }

    override fun getItem(position: Int): Fragment {
        return fragments.keys.elementAt(position)
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}