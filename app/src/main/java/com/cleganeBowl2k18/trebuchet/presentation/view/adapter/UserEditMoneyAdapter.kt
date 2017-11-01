package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * Created by khersey on 2017-10-25.
 */
class UserEditMoneyAdapter(private val mUsers: MutableList<User>,
                           private val mOnUserItemClickListener: UserCheckmarkAdapter.OnUserItemClickListener) :
        RecyclerView.Adapter<UserEditMoneyAdapter.UserViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView
    private var mOweSplit: MutableMap<Long, Long> = mutableMapOf()

    var users: List<User>
        get() = mUsers
        set(users) {
            this.mUsers.clear()
            this.mUsers.addAll(users)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEditMoneyAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_user_checkbox, parent, false)
        return UserViewHolder(view)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        val title = mUsers[position].getUserTitle()
        val content = mUsers[position].email
        val id = mUsers[position].externalId
        holder!!.bindData(title, content, id)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    fun getOweSplit(): MutableMap<Long, Long> {
        return mOweSplit
    }

    fun returnData(pair: Pair<Long, Long>?) {
        if (pair != null) {
            mOweSplit[pair.first] = pair.second
        }
    }

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.card_user_edit_money)
        lateinit var mCardView: CardView
        @BindView(R.id.user_label)
        lateinit var mTitleTV: TextView
        @BindView(R.id.user_content)
        lateinit var mContentTV: TextView
        @BindView(R.id.user_amount_edit_text)
        lateinit var mEditText: EditText

        @OnTextChanged(R.id.user_amount_edit_text)
        fun setSplitPair() {
            val amount: Long = (mEditText.text.toString().toDouble() * 10).toLong() // TODO remove hacky shit
            returnData(mId to amount)
        }

        var mId: Long = 0

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(title: String?, content: String?, id: Long) {
            mTitleTV.text = title
            mContentTV.text = content
            mId = id
        }

        @OnClick(R.id.create_group_card_view)
        fun onCardClicked() {
            mOnUserItemClickListener.onUserItemClick(mUsers[adapterPosition])
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
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as UserEditMoneyAdapter.UserViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as UserEditMoneyAdapter.UserViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as UserEditMoneyAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as UserEditMoneyAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }

}