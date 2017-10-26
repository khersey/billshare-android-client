package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.User

/**
 * Created by khersey on 2017-10-25.
 */
class UserCheckmarkAdapter(private val mUsers: MutableList<User>,
                           private val mOnUserItemClickListener: UserCheckmarkAdapter.OnUserItemClickListener) :
        RecyclerView.Adapter<UserCheckmarkAdapter.UserViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)
    }

    var users: List<User>
        get() = mUsers
        set(users) {
            this.mUsers.clear()
            this.mUsers.addAll(users)
            notifyDataSetChanged()
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
        holder!!.bindData(title, content)
    }

    override fun getItemCount(): Int {
        return mUsers.size
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

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(title: String?, content: String?) {
            mTitleTV.text = title
            mContentTV.text = content
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