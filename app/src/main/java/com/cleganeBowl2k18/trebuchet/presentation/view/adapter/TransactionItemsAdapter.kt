package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.TransactionDetailPresenter

/**
 * Created by khersey on 2017-11-08.
 */
class TransactionItemsAdapter(private var mTransaction: Transaction,
                              private var mGroup: Group,
                              private val mCurrentUserId: Long,
                              private val mOnTransactionItemClickListener: TransactionItemsAdapter.OnTransactionItemClickListener) :
        RecyclerView.Adapter<TransactionItemsAdapter.TransactionItemViewHolder>() {

    private var mCanResolve: Boolean = mCurrentUserId in mTransaction.paySplit
    private var mPresenter: TransactionDetailPresenter? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemsAdapter.TransactionItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_transaction_item, parent, false)
        return TransactionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder?, position: Int) {
        val userId: Long = mTransaction.lineItemMap.keys.toList()[position]
        val user: User? =  mGroup.users!!.find {user -> user.externalId == userId}
        val userLabel = "${user!!.fName} ${user!!.lName}"
        val oweAmount: Long? = mTransaction.oweSplit[userId]
        val payAmount: Long? = mTransaction.paySplit[userId]
        val resolvable: Boolean = mCanResolve && !mTransaction.resolved[userId]!!
        val lineId: Long? = mTransaction.lineItemMap[userId]

        holder!!.bindData(userLabel, oweAmount, payAmount, resolvable, lineId, mTransaction.id)
    }

    override fun getItemCount(): Int {
        return mTransaction.lineItemMap.size
    }

    fun setPresener(presenter: TransactionDetailPresenter) {
        mPresenter = presenter
    }


    interface OnTransactionItemClickListener {
        fun onTransactionItemClick(lineId: Long)
    }

    inner class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.transaction_item_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.user_label)
        lateinit var mUserLabelTV: TextView
        @BindView(R.id.owe_amount)
        lateinit var mOweAmountTV: TextView
        @BindView(R.id.paid_amount)
        lateinit var mPayAmountTV: TextView
        @BindView(R.id.resolve_button)
        lateinit var mResolveButton: Button

        var mLineId: Long = 0
        var mTransactionId: Long = 0

        @OnClick(R.id.resolve_button)
        fun resolveClicked() {
            mPresenter!!.resolveTransaction(mTransactionId, mLineId)
            mResolveButton.visibility = View.GONE
        }

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(userLabel: String?, oweAmount: Long?, payAmount: Long?, resolvable: Boolean, lineId: Long?, transactionId: Long?) {
            mUserLabelTV.text = userLabel
            if (oweAmount == null) {
                mOweAmountTV.visibility = View.GONE
            } else {
                mOweAmountTV.text = "owes: $${oweAmount.toDouble() * 0.01}"
            }

            if (payAmount != null) {
                mPayAmountTV.text = "paid: $${payAmount!!.toDouble() * 0.01}"
                mPayAmountTV.visibility = View.VISIBLE
            }

            if (resolvable) {
                mResolveButton.visibility = View.VISIBLE
            }

            mLineId = lineId!!
            mTransactionId = transactionId!!
        }

        @OnClick(R.id.transaction_item_card_view)
        fun onCardClicked() {
            mOnTransactionItemClickListener.onTransactionItemClick(mLineId)
        }
    }
}