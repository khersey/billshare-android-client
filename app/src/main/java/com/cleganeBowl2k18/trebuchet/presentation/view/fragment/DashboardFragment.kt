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
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionSummaryReceiver
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateGroupDetailIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateGroupIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionDetailItent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.DashboardAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.DashboardPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.DashboardView
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-22.
 */
class DashboardFragment : BaseFragment(), DashboardView, DashboardAdapter.OnDashboardItemClickListener {

    // TODO: put these in a shared constants file
    val CREATE_GROUP: Int = 0
    val CREATE_TRANSACTION: Int = 1
    val TRANSACTION_SUMMARY: Int = 2
    val NEW_GROUP: Int = 3
    val NEW_TRANSACTION: Int = 4

    @Inject
    lateinit var mPresenter: DashboardPresenter

    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0
    private val gson: Gson = Gson()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        prefs = activity.getSharedPreferences(Constants.PREFS_FILENAME, 0)
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

        getCardData()

        return view
    }

    private fun setupRecyclerView() {
        mDashboardAdapter = DashboardAdapter(this, mutableListOf(CREATE_GROUP to null,
                CREATE_TRANSACTION to null, TRANSACTION_SUMMARY to null))

        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.addItemDecoration(VerticalSpacingItemDecoration(30))
        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mDashboardAdapter
    }

    private fun getCardData() {
        // Other API calls
        mPresenter.getTransactionsSummary(mCurrentUserId)
        mPresenter.getRecentActivity(mCurrentUserId)
    }

    @BindView(R.id.dashboard_list)
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDashboardAdapter: DashboardAdapter

    override fun showError(message: String) {

    }

    override fun summaryReceived(summary: TransactionSummaryReceiver) {
        mDashboardAdapter.setCardAtPos(2, mDashboardAdapter.TRANSACTION_SUMMARY to summary)
    }

    override fun transactionsReceived(transactions: MutableList<Transaction>) {
        transactions.map { transaction ->
            mDashboardAdapter.addCard(mDashboardAdapter.NEW_TRANSACTION to (transaction to mCurrentUserId))
        }
    }

    override fun groupsReceived(groups: MutableList<Group>) {
        groups.map { group ->
            mDashboardAdapter.addCard(mDashboardAdapter.NEW_GROUP to group)
        }
    }

    // ON CLICKS
    override fun onCreateGroupClicked() {
        startActivityForResult(activity.CreateGroupIntent(), 1)
    }

    override fun onCreateTransactionClicked() {
        startActivityForResult(activity.CreateTransactionIntent(), 1)
    }

    override fun onGroupClicked(group: Group) {
        val intent = context.CreateGroupDetailIntent()
        intent.putExtra("group", gson.toJson(group))
        startActivity(intent)
    }

    override fun onTransactionClicked(transaction: Transaction) {
        val intent = activity.CreateTransactionDetailItent()
        intent.putExtra("transaction", gson.toJson(transaction))
        startActivity(intent)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            return fragment
        }
    }
}