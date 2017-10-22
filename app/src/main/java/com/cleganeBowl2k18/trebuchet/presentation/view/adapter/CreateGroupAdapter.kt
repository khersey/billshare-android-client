package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

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
import com.cleganeBowl2k18.trebuchet.data.entity.User
import android.R.attr.data



/**
 * Created by khersey on 2017-10-21.
 */
class CreateGroupAdapter(private val mUsers: MutableList<User>,
                         private val mOnUserItemClickListener: CreateGroupAdapter.OnUserItemClickListener) :
        RecyclerView.Adapter<CreateGroupAdapter.UserViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateGroupAdapter.UserViewHolder {
        var view = if (viewType == ADD_USER) {
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_add_user, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_create_group_user, parent, false)
        }
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreateGroupAdapter.UserViewHolder, position: Int) {

        if (position >= itemCount) {
            setHasLoadButton(true)
        } else {
            if (hasLoadButton) {
                setHasLoadButton(false)
            }

            

        }
        val title = mUsers[position].name
        val content = mUsers[position].users

        holder.bindData(title!!, content!!)
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
        when (position < getItemCount()) {
            true -> TITLE
            false -> ADD_USER
        }
    }

    fun isHasLoadButton(): Boolean {
        return hasLoadButton
    }

    fun setHasLoadButton(hasLoadButton: Boolean) {
        this.hasLoadButton = hasLoadButton
        notifyDataSetChanged()
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

        @OnClick(R.id.group_card_view)
        fun onCardClicked() {
            mOnUserItemClickListener.onUserItemClick(mUsers[adapterPosition])
        }
    }
}