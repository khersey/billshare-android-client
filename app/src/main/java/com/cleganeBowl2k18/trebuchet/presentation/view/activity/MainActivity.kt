package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.SectionsPagerAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.TransactionFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainActivity : BaseActivity(),
        GroupFragment.OnGroupSelectedListener,
        TransactionFragment.OnTransactionSelectedListener {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    val POSITION: String = "POSITION"

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var prefs: SharedPreferences? = null

    @BindView(R.id.container)
    lateinit var mViewPager: ViewPager

    @BindView(R.id.tabs)
    lateinit var mTabs: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(this.supportFragmentManager, this)
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.setOffscreenPageLimit(3)
        mTabs.setupWithViewPager(mViewPager)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(POSITION, mTabs.getSelectedTabPosition())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }
        if (id == R.id.action_logout) {
            val editor = prefs!!.edit()
            editor.putBoolean(Constants.LOGGED_IN, false)
            editor.apply()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun OnListFragmentInteractionListener() {
        //nothing
    }

    override fun onTransactionSelected(position: Int) {
        //TODO: go to detailed transaction view
    }

    override fun onGroupSelected(position: Int) {
        //TODO: go to detailed group view
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}

fun Context.MainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}
