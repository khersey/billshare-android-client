package com.cleganeBowl2k18.trebuchet.presentation.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateGroupIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.DashboardAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.DashboardPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.DashboardView
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-22.
 */
class DashboardFragment : BaseFragment(), DashboardView, DashboardAdapter.OnDashboardItemClickListener {

    @Inject
    lateinit var mPresenter: DashboardPresenter

    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        prefs = getActivity().getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        ButterKnife.bind(this, view)

        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        mDashboardAdapter = DashboardAdapter(this, mutableListOf(0, 1))

        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.addItemDecoration(VerticalSpacingItemDecoration(30))
        mRecyclerView.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mDashboardAdapter

    }


    @BindView(R.id.dashboard_list)
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDashboardAdapter: DashboardAdapter


    override fun onCreateGroupClicked() {
        startActivityForResult(activity.CreateGroupIntent(), 1)
    }

    override fun onCreateTransactionClicked() {
        startActivityForResult(activity.CreateTransactionIntent(), 1)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            return fragment
        }
    }
}