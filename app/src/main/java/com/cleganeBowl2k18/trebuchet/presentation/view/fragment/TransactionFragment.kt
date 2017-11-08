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
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionDetailItent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.TransactionListAdapter
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
class TransactionFragment : BaseFragment(), TransactionView, TransactionListAdapter.OnTransactionItemClickListener {

    @Inject
    lateinit var mPresenter: TransactionPresenter
    private val gson: Gson = Gson()
    private var mTransactions: MutableList<Transaction> = mutableListOf()

    private var mColumnCount = 1
    private var mListener: OnTransactionSelectedListener? = null
    private val VERTICAL_SPACING: Int = 30
    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0

    // Lifecycle Methods
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnTransactionSelectedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }

        prefs = getActivity().getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        mPresenter.fetchTransactionsByUser(mCurrentUserId)
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
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mPresenter.fetchTransactionsByUser(mCurrentUserId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("mTransactions", gson.toJson(mTransactions))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            mTransactions = gson.fromJson(savedInstanceState!!.getString("mTransactions"), object : TypeToken<MutableList<Transaction>>() {}.type)
        }
    }

    // Helper Methods
    private fun setupRecyclerView() {
        mTransactionListAdapter = TransactionListAdapter(mTransactions, this)

        mTransactionListRV.itemAnimator = DefaultItemAnimator()
        mTransactionListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mTransactionListRV.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        mTransactionListRV.setHasFixedSize(true)
        mTransactionListRV.adapter = mTransactionListAdapter

        mTransactionListAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    // UseCase stuff
    override fun showTransactions(transactions: List<Transaction>) {
        mTransactionListAdapter.transactions = transactions
        mTransactionListAdapter.notifyDataSetChanged()
        mTransactions = transactions.toMutableList()
    }

    override fun showTransactions() {
        mTransactionListAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        //To change body of created functions use File | Settings | File Templates.
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
    @BindView(R.id.transaction_list)
    lateinit var mTransactionListRV: RecyclerView
    lateinit var mTransactionListAdapter: TransactionListAdapter

    @BindView(R.id.empty_transaction_list)
    lateinit var mTransactionListEmptyView: TextView

    @BindView(R.id.progressbar_transaction)
    lateinit var mProgressBar: ContentLoadingProgressBar

    @OnClick(R.id.create_transaction_fab)
    fun createNewTransaction() {
        startActivityForResult(getActivity().CreateTransactionIntent(), 1)
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
        var intent = getActivity().CreateTransactionDetailItent()
        intent.putExtra("transaction", gson.toJson(transaction))
        startActivity(intent)
    }

    override fun onEditTransactionItemClick(transaction: Transaction) {
        //To change body of created functions use File | Settings | File Templates.
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
