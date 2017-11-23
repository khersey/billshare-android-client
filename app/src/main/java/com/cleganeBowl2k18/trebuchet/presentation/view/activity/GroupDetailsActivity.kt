package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserSmallAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by khersey on 2017-11-08.
 */
class GroupDetailsActivity: BaseActivity(), UserSmallAdapter.OnUserItemClickListener {

    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0
    private var mGroup: Group = Group()
    private var gson: Gson = Gson()
    lateinit var mUserSmallAdapter: UserSmallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        ButterKnife.bind(this)
        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        unpackIntent()

        setupRecyclerView()
        mGroupLabel.text = mGroup.label
    }

    override fun onDestroy() {
        super.onDestroy()
        // mPresenter.onDestroy()
        mUserSmallAdapter.unregisterAdapterDataObserver(mAdapterDataObserver)
    }

    private fun unpackIntent() {intent.getStringExtra("group")
        mGroup = gson.fromJson(intent.getStringExtra("group"), object : TypeToken<Group>() {}.type)
        // TODO: this but based on group theme
        val random = Random()
        when (random.nextInt(6)) {
            0 -> mGroupImage.setImageResource(R.drawable.champagne_theme_1x03)
            1 -> mGroupImage.setImageResource(R.drawable.condo_theme_1x03)
            2 -> mGroupImage.setImageResource(R.drawable.house_theme_1x03)
            3 -> mGroupImage.setImageResource(R.drawable.school_theme_1x03)
            4 -> mGroupImage.setImageResource(R.drawable.tropical_theme_1x03)
            5 -> mGroupImage.setImageResource(R.drawable.sandwich_theme_1x03)
        }
    }

    @BindView(R.id.user_list_recycle_view)
    lateinit var mUserListRV: RecyclerView

    @BindView(R.id.group_label)
    lateinit var mGroupLabel: TextView

    @BindView(R.id.group_image)
    lateinit var mGroupImage: ImageView

    private fun setupRecyclerView() {
        mUserSmallAdapter = UserSmallAdapter(mGroup.users!!.toMutableList(), this, false)

        mUserListRV.itemAnimator = DefaultItemAnimator()
        mUserListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mUserListRV.addItemDecoration(VerticalSpacingItemDecoration(20))
        mUserListRV.setHasFixedSize(true)
        mUserListRV.adapter = mUserSmallAdapter

        mUserSmallAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    override fun onUserItemClick(user: User) {
        // do nothing
    }

    override fun onEditUserItemClick(user: User) {
        // do nothing
    }

    override fun onAddUserClick() {
        // do nothing
    }

    private fun onUserListChanged() {
        //To change body of created functions use File | Settings | File Templates.
    }

    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            onUserListChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onUserListChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onUserListChanged()
        }
    }

}

fun Context.CreateGroupDetailIntent(): Intent {
    return Intent(this, GroupDetailsActivity::class.java)
}