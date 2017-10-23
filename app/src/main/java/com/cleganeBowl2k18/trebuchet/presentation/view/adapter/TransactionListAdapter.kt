package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.entity.User


class TransactionListAdapter(private val mTransactions: MutableList<Transaction>,
                             private val mOnTransactionItemClickListener: TransactionListAdapter.OnTransactionItemClickListener) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

    interface OnTransactionItemClickListener {
        fun onTransactionItemClick(transaction: Transaction)

        fun onEditTransactionItemClick(transaction: Transaction)
    }

    // allow user to get and set transactions as List
    var transactions: List<Transaction>
        get() = mTransactions
        set(transactions) {
            this.mTransactions.clear()
            this.mTransactions.addAll(transactions)
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: TransactionViewHolder?, position: Int) {
        val transactionGroup= mTransactions[position].group
        val transactionAmount = mTransactions[position].amount
        val transactionLabel = mTransactions[position].label
        val transactionOwed = mTransactions[position].oweSplit
        val transactionPayed = mTransactions[position].paySplit

        holder?.bindData(transactionGroup!!, transactionAmount!!, transactionLabel!!, transactionOwed!!, transactionPayed!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return mTransactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.transaction_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.transaction_card_title)
        lateinit var mTitleTV: TextView
        @BindView(R.id.transaction_card_subtitle)
        lateinit var mSubTitleTV: TextView
        @BindView(R.id.transaction_card_content)
        lateinit var mContentTV: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(group: Group, amount: Double, label: String, oweSplit: MutableMap<User, Double>, paySplit: MutableMap<User, Double>
        ) {

            // TODO: figure out who the current user is and use Splits to generate mContentTV for that User

            mTitleTV.text = label
            mSubTitleTV.text = group.label
            mContentTV.text = "$ ${amount}"
        }

        @OnClick(R.id.transaction_card_view)
        fun onCardClicked() {
            mOnTransactionItemClickListener.onTransactionItemClick(mTransactions[adapterPosition])
        }
    }

    private inner class AdapterItemTouchHelperCallback(dragDirs: Int, swipeDirs: Int) :
            ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // onGroupRemoved(viewHolder)
        }

        override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as TransactionListAdapter.TransactionViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as TransactionListAdapter.TransactionViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as TransactionListAdapter.TransactionViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as TransactionListAdapter.TransactionViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
