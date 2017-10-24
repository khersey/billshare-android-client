package com.cleganeBowl2k18.trebuchet.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateGroupIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateTransactionIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.TransactionListAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.TransactionPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionView
import java.util.*
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

    interface OnTransactionSelectedListener {
        fun onTransactionSelected(position: Int)
    }

    private var mColumnCount = 1
    private var mListener: OnTransactionSelectedListener? = null

    private val VERTICAL_SPACING: Int = 30

    @BindView(R.id.transaction_list)
    lateinit var mTransactionListRV: RecyclerView

    @BindView(R.id.empty_transaction_list)
    lateinit var mTransactionListEmptyView: TextView

    @BindView(R.id.progressbar_transaction)
    lateinit var mProgressBar: ContentLoadingProgressBar

    @Inject
    lateinit var mPresenter: TransactionPresenter

    lateinit var mTransactionListAdapter: TransactionListAdapter

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


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnTransactionSelectedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        mPresenter.fetchTransactions()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    @OnClick(R.id.create_transaction_fab)
    fun createNewTransaction() {
        startActivity(activity.CreateTransactionIntent())
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

    override fun showTransactions(transactions: List<Transaction>) {
        mTransactionListAdapter.transactions = transactions
    }

    override fun showTransactions() {
        mTransactionListAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTransactionItemClick(transaction: Transaction) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEditTransactionItemClick(transaction: Transaction) {
        //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupRecyclerView() {
        mTransactionListAdapter = TransactionListAdapter(ArrayList<Transaction>(0), this)

        mTransactionListRV.itemAnimator = DefaultItemAnimator()
        mTransactionListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mTransactionListRV.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        mTransactionListRV.setHasFixedSize(true)
        mTransactionListRV.adapter = mTransactionListAdapter

        mTransactionListAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    private fun onTransactionListChanged() {
        if (mTransactionListAdapter.itemCount == 0) {
            showEmptyView()
        } else {
            showListView()
        }
    }

    private fun showEmptyView() {
        mTransactionListEmptyView.visibility = View.VISIBLE
        mTransactionListRV.visibility = View.GONE
    }

    private fun showListView() {
        mTransactionListEmptyView.visibility = View.GONE
        mTransactionListRV.visibility = View.VISIBLE
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
