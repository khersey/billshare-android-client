package com.cleganeBowl2k18.trebuchet.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment.OnListFragmentInteractionListener


/**
 * [RecyclerView.Adapter] that can display a [Group] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupListAdapter(private val mGroups: MutableList<Group>,
                       private val mOnGroupItemClickListener: GroupListAdapter.OnGroupItemClickListener) :
        RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    lateinit private var mRecyclerView: RecyclerView

    var groups: List<Group>
        get() = mGroups
        set(groups) {
            this.mGroups.clear()
            this.mGroups.addAll(groups)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_group_tab, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindData(mGroups[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return mGroups.size
    }

    interface OnGroupItemClickListener {
        fun onGroupItemClick(group: Group)

        fun onEditGroupItemClick(group: Group)
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

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(group: Group) {
            mNameTV.text = group.label

            var content = "No Members"
            if (group.users!!.isNotEmpty()) {
                content = "Members: "
                group.users!!.forEach{ user -> content += "${user.fName}, "
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
        }

        @OnClick(R.id.group_card_view)
        fun onCardClicked() {
            mOnGroupItemClickListener.onGroupItemClick(mGroups[adapterPosition])
        }
    }
}
