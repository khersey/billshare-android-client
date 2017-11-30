package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindColor
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionSummaryReceiver

/**
 * Created by khersey on 2017-11-22.
 */
class DashboardAdapter(private val mOnDashboardItemClickListener: OnDashboardItemClickListener,
                       private var mCardQueue: MutableList<Pair<Int, Any?>>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

    val CREATE_GROUP: Int = 0
    val CREATE_TRANSACTION: Int = 1
    val TRANSACTION_SUMMARY: Int = 2
    val NEW_GROUP: Int = 3
    val NEW_TRANSACTION: Int = 4

    var cards: List<Pair<Int, Any?>>
        get() = mCardQueue
        set(cards) {
            this.mCardQueue.clear()
            this.mCardQueue.addAll(cards)
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder != null) {
            when (holder) {
                is TransactionSummaryHolder -> {
                    holder.bindData(mCardQueue[position].second as TransactionSummaryReceiver?)
                }
                is GroupViewHolder -> {
                    holder.bindData(mCardQueue[position].second as Group)
                }
                is TransactionViewHolder -> {
                    val pair = mCardQueue[position].second as Pair<Transaction, Long>
                    val userId = pair.second
                    val transaction = pair.first
                    holder.bindData(transaction, userId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            CREATE_GROUP -> {
                val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.card_dashboard_create_group, parent, false)
                return CreateGroupViewHolder(view)
            }
            CREATE_TRANSACTION -> {
                val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.card_dashboard_create_transaction, parent, false)
                return CreateTransactionViewHolder(view)
            }
            TRANSACTION_SUMMARY -> {
                val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.card_dashboard_transaction_summary, parent, false)
                return TransactionSummaryHolder(view)
            }
            NEW_GROUP -> {
                val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.card_group_tab, parent, false)
                return GroupViewHolder(view)
            }
            NEW_TRANSACTION -> {
                val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.card_transaction, parent, false)
                return TransactionViewHolder(view)
            }
        }

        // TODO: figure out how to get rid of this
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.card_dashboard_create_transaction, parent, false)
        return CreateTransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCardQueue.size
    }

    override fun getItemViewType(position: Int) : Int {
        return mCardQueue[position].first
    }

    fun addCard(card: Pair<Int, Any?>) {
        mCardQueue.add(card)
        notifyDataSetChanged()
    }

    fun setCardAtPos(position: Int, card: Pair<Int, Any?>) {
        mCardQueue[position] = card
        notifyDataSetChanged()
    }

    interface OnDashboardItemClickListener {

        fun onCreateGroupClicked()

        fun onCreateTransactionClicked()

        fun onGroupClicked(group: Group)

        fun onTransactionClicked(transaction: Transaction)

    }

    inner class CreateGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.dashboard_create_group)
        lateinit var mCardView: CardView

        @OnClick(R.id.create_group_button)
        fun createGroup() {
            mOnDashboardItemClickListener.onCreateGroupClicked()
        }

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    inner class CreateTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.dashboard_create_transaction)
        lateinit var mCardView: CardView

        @OnClick(R.id.create_transaction_button)
        fun createTransaction() {
            mOnDashboardItemClickListener.onCreateTransactionClicked()
        }

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    inner class TransactionSummaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.transaction_count)
        lateinit var mTransactionCount: TextView

        @BindView(R.id.summary_debt)
        lateinit var mSummaryDebt: TextView

        @BindView(R.id.summary_credit)
        lateinit var mSummaryCredit: TextView

        @JvmField @BindColor(R.color.red) @ColorInt
        internal var red: Int = 0

        @JvmField @BindColor (R.color.green) @ColorInt
        internal var green: Int = 0

        fun bindData(summary: TransactionSummaryReceiver?) {
            if (summary != null) {
                mTransactionCount.text = "${summary.transactionCount}"
                mSummaryDebt.text   = "$ -${(summary.debt * 100).toLong().toDouble()}"
                if (summary.debt == 0.0) {
                    mSummaryDebt.setTextColor(green)
                } else {
                    mSummaryDebt.setTextColor(red)
                }
                mSummaryCredit.text = "$ +${(summary.credit * 100).toLong().toDouble()}"
                if (summary.credit > 0.0) {
                    mSummaryCredit.setTextColor(green)
                } else if (summary.credit < 0.0) {
                    mSummaryCredit.setTextColor(red)
                }
            }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.group_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.group_label)
        lateinit var mNameTV: TextView
        @BindView(R.id.group_card_content)
        lateinit var mUsersTV: TextView
        @BindView(R.id.group_image)
        lateinit var mGroupImage: ImageView
        @BindView(R.id.group_is_new)
        lateinit var mIsNew: TextView

        var mGroup: Group = Group()

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(group: Group) {
            mGroup = group

            mNameTV.text = group.label

            var content = "No Members"
            if (group.users != null && group.users!!.isNotEmpty()) {
                content = "Members: "
                group.users!!.forEach {
                    user -> content += "${user.fName}, "
                }
                content = content.substringBeforeLast(',')
            }
            mUsersTV.text = content

            // TODO: this but based on the group's theme
            when ( (group.externalId % 6).toInt() ) {
                0 -> mGroupImage.setImageResource(R.drawable.champagne_theme_1x02)
                1 -> mGroupImage.setImageResource(R.drawable.condo_theme_1x02)
                2 -> mGroupImage.setImageResource(R.drawable.house_theme_1x02)
                3 -> mGroupImage.setImageResource(R.drawable.school_theme_1x02)
                4 -> mGroupImage.setImageResource(R.drawable.tropical_theme_1x02)
                5 -> mGroupImage.setImageResource(R.drawable.sandwich_theme_1x02)
            }

            mIsNew.visibility = View.VISIBLE
        }

        @OnClick(R.id.group_card_view)
        fun onCardClicked() {
            mOnDashboardItemClickListener.onGroupClicked(mGroup)
        }
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
        @BindView(R.id.transaction_is_new)
        lateinit var mIsNew: TextView

        var mTransaction: Transaction = Transaction()

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(transaction: Transaction, userId: Long) {

            mTransactionLabelTV.text = transaction.label
            mGroupLabelTV.text = transaction.group!!.label
            mAmountTotalTV.text = "$ ${transaction.amount.toDouble() * 0.01}"

            mTransaction = transaction

            var userTotal: Long = 0
            if (transaction.oweSplit[userId] != null) {
                userTotal -= transaction.oweSplit[userId]!!
            }
            if (transaction.paySplit[userId] != null) {
                userTotal += transaction.paySplit[userId]!!
            }

            if (userTotal < 0) {
                mAmountPaidOwedTV.text = "$ ${userTotal.toDouble() * 0.01} "
                mAmountPaidOwedTV.setTextColor(Color.rgb(191, 49, 17))
            } else {
                mAmountPaidOwedTV.text = "$ +${userTotal.toDouble() * 0.01} "
                mAmountPaidOwedTV.setTextColor(Color.rgb(65, 191, 30))
            }

            mCurrencyCodeTv.text = transaction.currencyCode
            mIsNew.visibility = View.VISIBLE
        }

        @OnClick(R.id.transaction_card_view)
        fun onCardClicked() {
            mOnDashboardItemClickListener.onTransactionClicked(mTransaction)
        }
    }

}