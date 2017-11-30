package com.cleganeBowl2k18.trebuchet.presentation.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionDetailItent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.TransactionListAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.dialog.TransactionFilterDialog
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.TransactionPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class TransactionFragment : BaseFragment(), TransactionView, TransactionListAdapter.OnTransactionItemClickListener,
        TransactionFilterDialog.TransactionFilterListener {

    @Inject
    lateinit var mPresenter: TransactionPresenter
    private val gson: Gson = Gson()

    private var mTransactions: MutableList<Transaction> = mutableListOf()
    private var mGroups: MutableList<Group> = mutableListOf()
    private var mColumnCount = 1
    private var mListener: OnTransactionSelectedListener? = null
    private val VERTICAL_SPACING: Int = 30
    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0

    private var mFilteredTransactions: MutableList<Transaction> = mutableListOf()
    private var mFilterType: String = "Newest"

    // Lifecycle Methods
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnTransactionSelectedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }

        prefs = activity.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        mPresenter.fetchTransactionsByUser(mCurrentUserId)
        mPresenter.fetchGroupsByUserId(mCurrentUserId)
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter.onDestroy()
        mTransactionListAdapter.unregisterAdapterDataObserver(mAdapterDataObserver)
        mListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_list, container, false)
        ButterKnife.bind(this, view)
        setupRecyclerView()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> mPresenter.fetchTransactionsByUser(mCurrentUserId)
                2 -> {
                    var transaction: Transaction = gson.fromJson(data!!.getStringExtra("updatedTransaction"), object : TypeToken<Transaction>() {}.type)
                    mTransactions[mTransactions.indexOfFirst { t -> t.id == transaction.id }] = transaction
                    mTransactionListAdapter.transactions = mTransactions
                    mTransactionListAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("mTransactions", gson.toJson(mTransactions))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            mTransactions = gson.fromJson(savedInstanceState.getString("mTransactions"), object : TypeToken<MutableList<Transaction>>() {}.type)
        }
    }

    // Helper Methods
    private fun setupRecyclerView() {
        mTransactionListAdapter = TransactionListAdapter(mFilteredTransactions, mCurrentUserId ,this)

        mTransactionListRV.itemAnimator = DefaultItemAnimator()
        mTransactionListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mTransactionListRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mTransactionListRV.setHasFixedSize(true)
        mTransactionListRV.adapter = mTransactionListAdapter

        mTransactionListAdapter.registerAdapterDataObserver(mAdapterDataObserver)

        applyFilter()
    }

    private fun applyFilter(groupId: Long? = null) {
        mSortFilterText.text = mFilterType

        Log.d("APPLY_FILTER", mFilterType)

        when (mFilterType) {
            "Newest" -> mFilteredTransactions = mTransactions
            "Oldest" -> mFilteredTransactions = mTransactions.asReversed()
            "Largest Amount" -> {
                mFilteredTransactions = mTransactions
                mFilteredTransactions = mFilteredTransactions.sortedWith(compareBy({it.amount})).asReversed().toMutableList()
            }
            "Smallest Amount" -> {
                mFilteredTransactions = mTransactions
                mFilteredTransactions = mFilteredTransactions.sortedWith(compareBy({it.amount})).toMutableList()
            }
            "I Paid" -> {
                mFilteredTransactions = mTransactions
                mFilteredTransactions = mFilteredTransactions.filter { item -> item.paySplit.containsKey(mCurrentUserId) }.toMutableList()
            }
            "I Owe Money" -> {
                mFilteredTransactions = mTransactions
                mFilteredTransactions = mFilteredTransactions.filter { item -> item.oweSplit.containsKey(mCurrentUserId) && item.resolved[mCurrentUserId] != null &&
                        item.resolved[mCurrentUserId] == false}.toMutableList()
            }
            "By Group" -> {
                mFilteredTransactions = mTransactions
                if (groupId != null) {
                    mFilteredTransactions = mFilteredTransactions.filter { item -> item.group!!.externalId == groupId }.toMutableList()
                }
            }
        }

        mTransactionListAdapter.transactions = mFilteredTransactions
        mTransactionListAdapter.notifyDataSetChanged()
    }

    // UseCase stuff
    override fun showTransactions(transactions: List<Transaction>) {
        mTransactionListAdapter.transactions = transactions
        mTransactionListAdapter.notifyDataSetChanged()
        mTransactions = transactions.toMutableList()
        if (mGroups.size > 0) {
            addGroupToTransaction()
        }
    }

    override fun showTransactions() {
        mTransactionListAdapter.notifyDataSetChanged()
    }

    override fun returnedGroups(groups: List<Group>) {
        mGroups = groups.toMutableList()
        if (mTransactions.size > 0) {
            addGroupToTransaction()
        }
    }

    override fun returnedGroups() {

    }

    override fun showError(message: String) {
        //To change body of created functions use File | Settings | File Templates.
    }

    private fun addGroupToTransaction() {
        for (transaction in mTransactions) {
            val thisGroup: Group? = mGroups.find {group -> group.externalId == transaction.group!!.externalId}
            if (thisGroup != null) {
                transaction.group = thisGroup
            }
        }
        applyFilter()
        mTransactionListAdapter.notifyDataSetChanged()
    }

    private fun onTransactionListChanged() {
        if (mTransactionListAdapter.itemCount == 0) {
            Log.e("TRANSACTIONS", "displaying EMPTY VIEW")
            showEmptyView()
        } else {
            showListView()
        }

    }

    // UI Elements
    @BindView(R.id.sort_filter_type)
    lateinit var mSortFilterText: TextView

    @BindView(R.id.transaction_list)
    lateinit var mTransactionListRV: RecyclerView
    lateinit var mTransactionListAdapter: TransactionListAdapter

    @BindView(R.id.empty_transaction_list)
    lateinit var mTransactionListEmptyView: TextView

    @BindView(R.id.progressbar_transaction)
    lateinit var mProgressBar: ContentLoadingProgressBar

    @OnClick(R.id.sort_filter_button)
    fun createSortFilterDialog() {
        var newFragment = TransactionFilterDialog()
        newFragment.show(fragmentManager, "sort/filter")
    }

    @OnClick(R.id.create_transaction_fab)
    fun createNewTransaction() {
        startActivityForResult(activity.CreateTransactionIntent(), 1)
    }

    override fun showProgress() {
        mTransactionListEmptyView.visibility = View.GONE
        mTransactionListRV.visibility = View.GONE
        mProgressBar.show()
    }

    override fun hideProgress() {
        mTransactionListEmptyView.visibility = View.VISIBLE
        mTransactionListRV.visibility = View.VISIBLE
        mProgressBar.hide()
    }

    override fun onTransactionItemClick(transaction: Transaction) {
        var intent = activity.CreateTransactionDetailItent()
        intent.putExtra("transaction", gson.toJson(transaction))
        startActivityForResult(intent, 2)
    }

    override fun onEditTransactionItemClick(transaction: Transaction) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFilterSelected(option: String) {
        mFilterType = option
        applyFilter()
    }

    override fun onGroupFilterSelect(groupId: Long) {
        mFilterType = "By Group"
        applyFilter(groupId)
    }

    private fun showEmptyView() {
        mTransactionListEmptyView.visibility = View.VISIBLE
        mTransactionListRV.visibility = View.GONE
    }

    private fun showListView() {
        mTransactionListEmptyView.visibility = View.GONE
        mTransactionListRV.visibility = View.VISIBLE
    }

    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            onTransactionListChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onTransactionListChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onTransactionListChanged()
        }
    }

    interface OnTransactionSelectedListener {
        fun onTransactionSelected(position: Int)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): TransactionFragment {
            val fragment = TransactionFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
