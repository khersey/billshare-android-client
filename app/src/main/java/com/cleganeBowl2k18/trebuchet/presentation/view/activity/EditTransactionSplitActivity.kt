package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.SplitUtil
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserCheckmarkAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserEditMoneyAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_edit_transaction_split.*
import java.io.Serializable
import java.util.*

class EditTransactionSplitActivity : BaseActivity(), UserEditMoneyAdapter.OnUserItemClickListener, UserCheckmarkAdapter.OnUserItemClickListener {

    private val VERTICAL_SPACING: Int = 30
    private val gson: Gson = Gson()

    var mUsers : MutableList<User>? = null
    var mCurrentUser : User? = null // TODO: use shared preferences for current user
    var mAmount : Long = 0
    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0

    // Lifecycle Events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)
        setContentView(R.layout.activity_edit_transaction_split)
        setSupportActionBar(toolbar)

        mAmount = getIntent().getLongExtra("amount", 0)
        mUsers = gson.fromJson(getIntent().getStringExtra("users"), object : TypeToken<List<User>>() {}.type)
        val firstID: Long = 1
        mCurrentUser = mUsers!!.find { user -> user.externalId == firstID }


        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        setupSpinners()
        setupRecycleViews()
    }

    override fun onUserItemClick(user: User) {

    }


    private fun setupSpinners() {
        mPaidBySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mUsers!!.map { user: User -> "${user.fName!!} ${user.lName!![0]}." })
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

        mAmountSplitAdapter = UserEditMoneyAdapter(ArrayList<User>(0), this)

        mAmountSplitRV.itemAnimator = DefaultItemAnimator()
        mAmountSplitRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mAmountSplitRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAmountSplitRV.setHasFixedSize(true)
        mAmountSplitRV.adapter = mAmountSplitAdapter
    }

    // UI Elements
    @BindView(R.id.toolbar_title)
    lateinit var mToolbarTitle : TextView

    @BindView(R.id.paid_by_spinner)
    lateinit var mSpinnerPaidBy : Spinner
    private var mSpinnerPaidByPos : Int = 0

    @BindView(R.id.split_type_spinner)
    lateinit var mSpinnerSplitType : Spinner
    private var mSpinnerSplitTypePos : Int = 0

    @BindView(R.id.transaction_equal_split)
    lateinit var mContainerEqualSplit : LinearLayout

    @BindView(R.id.equal_split_recycle_view)
    lateinit var mEqualSplitRV : RecyclerView
    lateinit var mEqualSplitAdapter: UserCheckmarkAdapter

    @BindView(R.id.amount_split_recycler_view)
    lateinit var mAmountSplitRV : RecyclerView
    lateinit var mAmountSplitAdapter: UserEditMoneyAdapter

    @BindView(R.id.transaction_amount_split)
    lateinit var mContainerAmountSplit : LinearLayout

    lateinit var mPaidBySpinnerAdapter: ArrayAdapter<String>

    @OnItemSelected(R.id.split_type_spinner)
    fun onSplitTypeChanged(pos: Int) {
        mContainerAmountSplit.visibility = View.GONE
        mContainerEqualSplit.visibility = View.GONE
        val selection = mSpinnerSplitType.getItemAtPosition(pos)

        when (selection) {
            "Equally"   -> mContainerEqualSplit.visibility = View.VISIBLE
            "By Amount" -> mContainerAmountSplit.visibility = View.VISIBLE
        }
        mSpinnerSplitTypePos = pos
    }

    @OnItemSelected(R.id.paid_by_spinner)
    fun onPaidByPosChange(pos: Int) {
        mSpinnerPaidByPos = pos
    }

    @OnClick(R.id.save_button)
    fun saveTransaction() {
        if (formIsValid()) {

            val paySplit = buildPaySplit()
            val oweSplit = buildOweSplit()

            var intent : Intent = Intent()
            intent.putExtra("oweSplit", (oweSplit as Serializable))
            intent.putExtra("paySplit", (paySplit as Serializable))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun formIsValid() : Boolean {
        // TODO: Form Validation
        return true
    }

    fun buildPaySplit() : MutableMap<Long, Long> {
        return mutableMapOf(mUsers!![mSpinnerPaidByPos].externalId to mAmount)
    }

    fun buildOweSplit(): MutableMap<Long, Long> {
        var oweSplit : MutableMap<Long, Long> = mutableMapOf()

        when (mSpinnerSplitType.getItemAtPosition(mSpinnerSplitTypePos)) {
            "Equally" -> {
                val userIds: List<Long> = mEqualSplitAdapter.getUserIds()
                oweSplit = SplitUtil.equalSplit(mAmount, userIds)
            }
            "By Amount" -> {
                oweSplit = mAmountSplitAdapter.getOweSplit()
            }
        }

        return oweSplit
    }
}

fun Context.CreateEditTransactionIntent(): Intent {
    return Intent(this, EditTransactionSplitActivity::class.java)
}