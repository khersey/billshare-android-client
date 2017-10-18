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
import com.cleganeBowl2k18.trebuchet.R.id.content
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User

import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment.OnListFragmentInteractionListener


/**
 * [RecyclerView.Adapter] that can display a [Group] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupListAdapter(private val mGroups: MutableList<Group>,
                       private val mListener: OnListFragmentInteractionListener?) :
        RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_group, parent, false)
        return ViewHolder(view)
    }

    lateinit private var mRecyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mGroup = mGroups[position]
        holder.mIdView.text = mGroups[position].name
        holder.mContentView.text = mGroups[position].getUsersAsStr()

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mGroup)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mRecyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return mGroups.size
    }

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.group_card_view)
        lateinit var mCardView: CardView
        @BindView(R.id.group_card_name)
        lateinit var mNameTV: TextView
        @BindView(R.id.group_card_content)
        lateinit var mUsersTV: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(groupName: String, groupUsers: List<User>) {
            mNameTV.text = groupName

            var content = ""
            if (groupUsers != null && groupUsers?.size != 0) {
                content = "Members: "
                groupUsers?.forEach{ user -> content += "${user.fName}, "}
                content.substringBeforeLast(',')
            } else {
                content
            }
            mUsersTV.text = content
        }

        @OnClick(R.id.group_card_view)
        fun onCardClicked() {
            mOnPetItemClickListener.onPetItemClick(mGroups[adapterPosition])
        }
    }


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mGroup: Group? = null

        init {
            mIdView = mView.findViewById<View>(R.id.group_card_name) as TextView
            mContentView = mView.findViewById<View>(R.id.group_card_content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
