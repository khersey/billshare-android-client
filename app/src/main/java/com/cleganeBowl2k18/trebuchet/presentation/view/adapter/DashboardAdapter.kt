package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R

/**
 * Created by khersey on 2017-11-22.
 */
class DashboardAdapter(private val mOnDashboardItemClickListener: OnDashboardItemClickListener,
                       private var mCardQueue: MutableList<Int>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

    private val CREATE_GROUP: Int = 0
    private val CREATE_TRANSACTION: Int = 1

    var cards: List<Int>
        get() = mCardQueue
        set(cards) {
            this.mCardQueue.clear()
            this.mCardQueue.addAll(cards)
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

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
        return mCardQueue[position]
    }

    interface OnDashboardItemClickListener {

        fun onCreateGroupClicked()

        fun onCreateTransactionClicked()

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

}