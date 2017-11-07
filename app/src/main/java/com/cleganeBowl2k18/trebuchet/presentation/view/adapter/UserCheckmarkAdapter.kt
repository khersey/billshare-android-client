package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * Created by khersey on 2017-10-25.
 */
class UserCheckmarkAdapter(private val mUsers: MutableList<User>,
                           private val mOweSplit: MutableMap<Long, Long>,
                           private val mOnUserItemClickListener: UserCheckmarkAdapter.OnUserItemClickListener) :
        RecyclerView.Adapter<UserCheckmarkAdapter.UserViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView
    private var mUsersInSplit: MutableMap<Long, Boolean> = mutableMapOf()

    var users: List<User>
        get() = mUsers
        set(users) {
            this.mUsers.clear()
            this.mUsers.addAll(users)
            configureUserBooleanMap()
            notifyDataSetChanged()
        }

    init {
        configureUserBooleanMap()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCheckmarkAdapter.UserViewHolder {
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
        val userId = mUsers[position].externalId
        holder!!.bindData(title, content, userId)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    private fun configureUserBooleanMap() {
        mUsersInSplit = mutableMapOf()
        for (user in mUsers) {
            mUsersInSplit[user.externalId] = mOweSplit.containsKey(user.externalId)
        }
    }

    fun getUserIds(): List<Long> {
        return mUsersInSplit.keys.filter { id -> mUsersInSplit[id] == true }
    }

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.card_user_checkbox)
        lateinit var mCardView: CardView
        @BindView(R.id.user_label)
        lateinit var mTitleTV: TextView
        @BindView(R.id.user_content)
        lateinit var mContentTV: TextView
        @BindView(R.id.user_checkbox)
        lateinit var mCheckbox: CheckBox

        var mId: Long = 0

        @OnCheckedChanged(R.id.user_checkbox)
        fun checkboxChanged() {
            try {
                mUsersInSplit[mId] = mCheckbox.isChecked
            } catch(e: Exception) {
                Log.e("CHECKBOX_ERROR", "something went horribly wrong while clicking a checkbox...", e)
            }
        }

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(title: String?, content: String?, id: Long) {
            mTitleTV.text = title
            mContentTV.text = content
            mId = id
            mCheckbox.isChecked = (mUsersInSplit[mId] == null || mUsersInSplit[mId] == true)
            checkboxChanged()
        }

        @OnClick(R.id.card_user_checkbox)
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
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as UserCheckmarkAdapter.UserViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as UserCheckmarkAdapter.UserViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as UserCheckmarkAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as UserCheckmarkAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }

}