package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction


class TransactionListAdapter(private val mTransactions: MutableList<Transaction>,
                             private val mCurrentUserId: Long,
                             private val mOnTransactionItemClickListener: TransactionListAdapter.OnTransactionItemClickListener) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

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
        val transactionCurrency = mTransactions[position].currencyCode

        holder?.bindData(transactionGroup!!, transactionAmount!!.toDouble() * 0.01,
                transactionLabel!!, transactionOwed!!, transactionPayed!!, transactionCurrency!!, mCurrentUserId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_transaction, parent, false)
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

    interface OnTransactionItemClickListener {
        fun onTransactionItemClick(transaction: Transaction)

        fun onEditTransactionItemClick(transaction: Transaction)
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.transaction_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.transaction_label)
        lateinit var mTransactionLabelTV: TextView
        @BindView(R.id.group_label)
        lateinit var mGroupLabelTV: TextView
        @BindView(R.id.amount_owed_paid)
        lateinit var mAmountPaidOwedTV: TextView
        @BindView(R.id.amount_total)
        lateinit var mAmountTotalTV: TextView
        @BindView(R.id.currency_code)
        lateinit var mCurrencyCodeTv: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(group: Group, amount: Double, label: String, oweSplit: MutableMap<Long, Long>, paySplit: MutableMap<Long, Long>, currencyCode: String, userId: Long) {

            mTransactionLabelTV.text = label
            mGroupLabelTV.text = group.label
            mAmountTotalTV.text = "$ ${amount}"

            var userTotal: Long = 0
            if (oweSplit[userId] != null) {
                userTotal -= oweSplit[userId]!!
            }
            if (paySplit[userId] != null) {
                userTotal += paySplit[userId]!!
            }

            if (userTotal < 0) {
                mAmountPaidOwedTV.text = "$ ${userTotal.toDouble() * 0.01} "
                mAmountPaidOwedTV.setTextColor(Color.rgb(191, 49, 17))
            } else {
                mAmountPaidOwedTV.text = "$ +${userTotal.toDouble() * 0.01} "
                mAmountPaidOwedTV.setTextColor(Color.rgb(65, 191, 30))
            }

            mCurrencyCodeTv.text = currencyCode
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
