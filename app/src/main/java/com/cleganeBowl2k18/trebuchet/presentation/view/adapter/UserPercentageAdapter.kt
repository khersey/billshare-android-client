package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * Created by khersey on 2017-11-27.
 */
class UserPercentageAdapter (private val mUsers: MutableList<User>,
                             private val mPercentSplit: MutableMap<Long, Long>):
        RecyclerView.Adapter<UserPercentageAdapter.UserViewHolder>() {

    var users: List<User>
        get() = mUsers
        set(users) {
            this.mUsers.clear()
            this.mUsers.addAll(users)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPercentageAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_user_percentage, parent, false)
        return UserViewHolder(view)

    }


    override fun onBindViewHolder(holder: UserPercentageAdapter.UserViewHolder?, position: Int) {
        holder!!.bindData(mUsers[position])
    }


    override fun getItemCount(): Int {
        return mUsers.size
    }

    fun getPercentSplit() : MutableMap<Long, Long> {
        return mPercentSplit
    }

    fun returnData(pair: Pair<Long, Long>?) {
        if (pair != null) {
            mPercentSplit[pair.first] = pair.second
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.user_edit_money_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.user_label)
        lateinit var mTitleTV: TextView
        @BindView(R.id.user_content)
        lateinit var mContentTV: TextView
        @BindView(R.id.user_amount_edit_text)
        lateinit var mEditText: EditText

        @OnTextChanged(R.id.user_amount_edit_text)
        fun setSplitPair() {
            try {
                val amount: Long = mEditText.text.toString().toLong() // TODO remove hacky shit
                returnData(mId to amount)
            } catch (e: Exception) { // if user deletes entire string
                returnData(mId to 0)
            }

        }

        var mId: Long = 0

        fun bindData(user: User) {
            mTitleTV.text = user.getUserTitle()
            mContentTV.text = user.email
            mId = user.externalId
            val amount: Long? = mPercentSplit[mId]
            if (amount != null) {
                mEditText.setText("${amount}")
            } else {
                mEditText.setText("0")
            }

        }
    }
}