package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserSmallAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.CreateGroupPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateGroupView
import java.util.*
import javax.inject.Inject

class CreateGroupActivity : BaseActivity(), CreateGroupView,
        UserSmallAdapter.OnUserItemClickListener {

    // class variables
    private val VERTICAL_SPACING: Int = 30

    @Inject
    lateinit var mPresenter: CreateGroupPresenter

    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0

    // Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        ButterKnife.bind(this)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        setupRecyclerView()
        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            fun onClick(view: View) {
                // TODO: form validation
                this.finish()
            }
        })

        // GET current user
        mPresenter.getUser(mCurrentUserId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        // mPresenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
        mUserSmallAdapter.unregisterAdapterDataObserver(mAdapterDataObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val email : String? = data?.getStringExtra("email")

            if (email != null) {
                val user = User(0, null, email, null, null)
                mUserSmallAdapter.addUser(user)
            } else {
                Log.e("onActivityResultERROR", "AddUserReturned null!")
            }
        }
    }

    // Helper functions
    private fun setupRecyclerView() {
        mUserSmallAdapter = UserSmallAdapter(ArrayList<User>(0), this, true)

        mUserListRV.itemAnimator = DefaultItemAnimator()
        mUserListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mUserListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mUserListRV.setHasFixedSize(true)
        mUserListRV.adapter = mUserSmallAdapter

        mUserSmallAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    // useCase Callbacks
    override fun userFetched(user: User) {
        mUserSmallAdapter.addUser(user)
    }

    override fun groupCreated() {
        setResult(Activity.RESULT_OK, Intent())
        this.finish()
    }

    override fun showError(message: String) {

    }
    // useCase Callbacks end

    // UI Elements
    @BindView(R.id.create_group_recycle_view)
    lateinit var mUserListRV: RecyclerView
    lateinit var mUserSmallAdapter: UserSmallAdapter

    @BindView(R.id.group_label_text_input)
    lateinit var mGroupLabelInput: TextInputEditText

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    // UI methods
    private fun onUserListChanged() {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddUserClick() {
        startActivityForResult(this.CreateAddUserByEmailIntent(), 1)
    }

    @OnClick(R.id.save_new_group)
    fun onSaveGroupClicked() {
        // TODO: add form validation
        val groupLabel : String? = mGroupLabelInput.text.toString()

        val users : List<User> = mUserSmallAdapter.users

        val group: Group = Group(0, groupLabel, users)

        mPresenter.onSaveGroup(group)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onUserItemClick(user: User) {

    }

    override fun onEditUserItemClick(user: User) {

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

fun Context.CreateGroupIntent(): Intent {
    return Intent(this, CreateGroupActivity::class.java)
}
