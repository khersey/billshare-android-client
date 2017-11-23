package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.MainActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.DashboardFragment
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.TransactionFragment

/**
 * This adapter controls tab switching in the MainActivity
 */
class SectionsPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private var mContext: Context

    init {
        mContext = context
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position) {
            0 -> return mContext.getString(R.string.tab_dashboard)
            1 -> return mContext.getString(R.string.tab_groups)
            2 -> return mContext.getString(R.string.tab_transactions)
        }
        return "ERROR"
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return DashboardFragment.newInstance()
            1 -> return GroupFragment.newInstance(1)
            2 -> return TransactionFragment.newInstance(1)
        }
        return MainActivity.PlaceholderFragment.newInstance(1)
    }

    override fun getCount(): Int {
        return 3
    }
}