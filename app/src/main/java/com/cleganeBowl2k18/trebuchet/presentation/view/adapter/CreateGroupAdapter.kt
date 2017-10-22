package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.User
import android.widget.Toast
import android.R.attr.button




/**
 * Created by khersey on 2017-10-21.
 */
class CreateGroupAdapter(private val mUsers: MutableList<User>,
                         private val mOnUserItemClickListener: CreateGroupAdapter.OnUserItemClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView
    private var hasLoadButton : Boolean = true

    // CONSTANTS
    private val TITLE : Int = 0
    private val ADD_USER : Int = 1

    interface OnUserItemClickListener {
        fun onUserItemClick(user: User)

        fun onEditUserItemClick(user: User)

        fun onAddUserClick()
    }

    var users: List<User>
        get() = mUsers
        set(users) {
            this.mUsers.clear()
            this.mUsers.addAll(users)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ADD_USER) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_add_user, parent, false)
            return AddUserViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_create_group_user, parent, false)
            return UserViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position >= itemCount) {
            setHasLoadButton(true)
            @Bind(R.id.create_group_add_user)
            holder.mAddUserBtn

            holder.mAddUserBtn.setOnClickListener(View.OnClickListener { Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show() })
        } else {
            if (hasLoadButton) {
                setHasLoadButton(false)
            }
            var title = mUsers[position].email
            var content = "invite sent"

            if (mUsers[position].fName != null && mUsers[position].lName != null ) {
                title = "${mUsers[position].fName} ${mUsers[position].lName}"
                content = mUsers[position].email!!
            }

            holder.bindData(title!!, content!!)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        if (hasLoadButton) {
            return mUsers.size + 1
        } else {
            return mUsers.size
        }
    }

    override fun getItemViewType(position: Int) : Int {
        // if statements are so last century
        when (position < itemCount) {
            true -> return TITLE
            false -> return ADD_USER
        }
    }

    fun isHasLoadButton(): Boolean {
        return hasLoadButton
    }

    fun setHasLoadButton(hasLoadButton: Boolean) {
        this.hasLoadButton = hasLoadButton
        notifyDataSetChanged()
    }


    fun addUser(user: User) {
        mUsers.add(user)
    }


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.create_group_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.create_group_title)
        lateinit var mTitleTV: TextView
        @BindView(R.id.create_group_content)
        lateinit var mContentTV: TextView

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

    private inner class AddUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.add_user_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.create_group_add_user)
        lateinit var mAddUserBtn: Button

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.create_group_add_user)
        fun onCardClicked() {
            mOnUserItemClickListener.onAddUserClick()
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
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as CreateGroupAdapter.UserViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as CreateGroupAdapter.UserViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as CreateGroupAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as CreateGroupAdapter.UserViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }
}