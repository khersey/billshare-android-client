package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.graphics.Canvas
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
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment.OnListFragmentInteractionListener


/**
 * [RecyclerView.Adapter] that can display a [Group] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupListAdapter(private val mGroups: MutableList<Group>,
                       private val mOnGroupItemClickListener: GroupListAdapter.OnGroupItemClickListener) :
        RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    interface OnGroupItemClickListener {
        fun onGroupItemClick(group: Group)

        fun onEditGroupItemClick(group: Group)
    }

    var groups: List<Group>
        get() = mGroups
        set(groups) {
            this.mGroups.clear()
            this.mGroups.addAll(groups)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_group, parent, false)
        return GroupViewHolder(view)
    }

    lateinit private var mRecyclerView: RecyclerView

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupName = mGroups[position].name
        val groupUsers = mGroups[position].users

        holder.bindData(groupName!!, groupUsers!!)
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

            var content = "No Members"
            if (groupUsers != null && groupUsers?.size != 0) {
                content = "Members: "
                groupUsers?.forEach{
                    user -> content += "${user.fName}, "
                }
                content = content.substringBeforeLast(',')
            }
            mUsersTV.text = content
        }

        @OnClick(R.id.group_card_view)
        fun onCardClicked() {
            mOnGroupItemClickListener.onGroupItemClick(mGroups[adapterPosition])
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
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as GroupListAdapter.GroupViewHolder).mCardView)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as GroupListAdapter.GroupViewHolder).mCardView)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                    (viewHolder as GroupListAdapter.GroupViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                    (viewHolder as GroupListAdapter.GroupViewHolder).mCardView, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
