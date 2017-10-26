package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnItemSelected
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.CreateGroupAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserCheckmarkAdapter
import kotlinx.android.synthetic.main.activity_edit_transaction_split.*
import java.util.ArrayList

class EditTransactionSplitActivity : BaseActivity() {

    @BindView(R.id.toolbar_title)
    lateinit var mToolbarTitle : TextView

    @BindView(R.id.paid_by_spinner)
    lateinit var mSpinnerPaidBy : Spinner

    @BindView(R.id.split_type_spinner)
    lateinit var mSpinnerSplitType : Spinner

    @BindView(R.id.transaction_equal_split)
    lateinit var mContainerEqualSplit : LinearLayout

    @BindView(R.id.equal_split_recycle_view)
    lateinit var mEqualSplitRV : RecyclerView

    lateinit var mEqualSplitAdapter: UserCheckmarkAdapter

    @BindView(R.id.amount_split_recycler_view)
    lateinit var mAmountSplitRV : RecyclerView



    @BindView(R.id.transaction_amount_split)
    lateinit var mContainerAmountSplit : LinearLayout

    lateinit var mPaidBySpinnerAdapter: ArrayAdapter<String>

    var mUsers : MutableList<User>? = (intent.getSerializableExtra("users") as MutableList<User>)
    var mCurrentUser : User? = mUsers.find {user -> user.externalId == (1 as Long)} // TODO: use shared preferences for current user
    var mAmount : Double = intent.getDoubleExtra("amount", 0.0)
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        setContentView(R.layout.activity_edit_transaction_split)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        setupSpinners()
        setupRecycleViews()
    }

    private fun setupSpinners() {
        mPaidBySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mUsers.map { user: User -> "${user.fName!!} ${user.lName!![0]}." })
        mPaidBySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerPaidBy.adapter = mPaidBySpinnerAdapter

        onSplitTypeChanged(0)
    }

    private fun setupRecycleViews() {
        mEqualSplitAdapter = UserCheckmarkAdapter(ArrayList<User>(0), this)

        mEqualSplitRV.itemAnimator = DefaultItemAnimator()
        mEqualSplitRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mEqualSplitRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mEqualSplitRV.setHasFixedSize(true)
        mEqualSplitRV.adapter = mEqualSplitAdapter

        mCreateGroupAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    @OnItemSelected(R.id.split_type_spinner)
    private fun onSplitTypeChanged(pos: Int) {
        mContainerAmountSplit.visibility = View.GONE
        mContainerEqualSplit.visibility = View.GONE
        val selection = mSpinnerSplitType.getItemAtPosition(pos)

        when (selection) {
            "Equally"   -> mContainerEqualSplit.visibility = View.VISIBLE
            "By Amount" -> mContainerAmountSplit.visibility = View.VISIBLE
        }
    }

    @OnClick(R.id.save_button)
    private fun onSave() {

    }







}
