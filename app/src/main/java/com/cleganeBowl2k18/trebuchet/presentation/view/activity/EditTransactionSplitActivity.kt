package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserPercentageAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_edit_transaction_split.*

class EditTransactionSplitActivity : BaseActivity(),
        UserEditMoneyAdapter.OnUserItemClickListener, UserCheckmarkAdapter.OnUserItemClickListener {

    private val VERTICAL_SPACING: Int = 30
    private val gson: Gson = Gson()

    var mUsers : MutableList<User>? = null
    var mCurrentUser : User? = null
    var mAmount : Long = 0
    var mSplitType : Int = Constants.SPLIT_EQUALLY
    private var mOweSplit: MutableMap<Long, Long> = mutableMapOf()
    private var mPaySplit: MutableMap<Long, Long> = mutableMapOf()
    private var mPercentSplit: MutableMap<Long, Long> = mutableMapOf()
    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0


    // Lifecycle Events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)
        setContentView(R.layout.activity_edit_transaction_split)
        setSupportActionBar(toolbar)

        unpackIntent()

        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupSpinners()
        setupRecycleViews()
    }

    override fun onUserItemClick(user: User) {

    }

    // helper methods
    private fun unpackIntent() {
        mAmount = intent.getLongExtra("amount", 0)
        mUsers = gson.fromJson(intent.getStringExtra("users"), object : TypeToken<MutableList<User>>() {}.type)
        mCurrentUser = mUsers!!.find { user -> user.externalId == mCurrentUserId }
        mSplitType = intent.getIntExtra("splitType", Constants.SPLIT_EQUALLY)

        mOweSplit = gson.fromJson(intent.getStringExtra("oweSplit"), object : TypeToken<MutableMap<Long, Long>>() {}.type)
        mPaySplit = gson.fromJson(intent.getStringExtra("paySplit"), object : TypeToken<MutableMap<Long, Long>>() {}.type)
        if (mSplitType != Constants.SPLIT_BY_PERCENTAGE) {
            mPercentSplit = SplitUtil.precentageSplit(mAmount, mOweSplit)
        } else {
            mPercentSplit = mOweSplit
        }

    }



    private fun setupSpinners() {
        mPaidBySpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mUsers!!.map { user: User -> "${user.fName!!} ${user.lName!![0]}." })
        mPaidBySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerPaidBy.adapter = mPaidBySpinnerAdapter

        // TODO: support multiple paying users
        mPaySplit.keys.forEach { userId -> mSpinnerPaidBy.setSelection(mUsers!!.indexOfFirst { user -> user.externalId == userId }) }

        mSplitTypeSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Equally", "By Amount", "By Percent"))
        mSplitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerSplitType.adapter = mSplitTypeSpinnerAdapter
        when (mSplitType) {
            Constants.SPLIT_EQUALLY -> mSpinnerSplitType.setSelection(0)
            Constants.SPLIT_BY_AMOUNT -> mSpinnerSplitType.setSelection(1)
            Constants.SPLIT_BY_PERCENTAGE -> mSpinnerSplitType.setSelection(2)
        }
        onSplitTypeChanged()
    }

    private fun setupRecycleViews() {
        mEqualSplitAdapter = UserCheckmarkAdapter(mUsers!!, mOweSplit, this)

        mEqualSplitRV.itemAnimator = DefaultItemAnimator()
        mEqualSplitRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mEqualSplitRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mEqualSplitRV.setHasFixedSize(true)
        mEqualSplitRV.adapter = mEqualSplitAdapter

        mAmountSplitAdapter = UserEditMoneyAdapter(mUsers!!, mOweSplit, this)

        mAmountSplitRV.itemAnimator = DefaultItemAnimator()
        mAmountSplitRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mAmountSplitRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAmountSplitRV.setHasFixedSize(true)
        mAmountSplitRV.adapter = mAmountSplitAdapter

        mPercentSplitAdapter = UserPercentageAdapter(mUsers!!, mPercentSplit)

        mPercentSplitRV.itemAnimator = DefaultItemAnimator()
        mPercentSplitRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mPercentSplitRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPercentSplitRV.setHasFixedSize(true)
        mPercentSplitRV.adapter = mPercentSplitAdapter
    }

    // UI Elements
    @BindView(R.id.toolbar_title)
    lateinit var mToolbarTitle : TextView

    @BindView(R.id.paid_by_spinner)
    lateinit var mSpinnerPaidBy : Spinner
    lateinit var mPaidBySpinnerAdapter: ArrayAdapter<String>

    @BindView(R.id.split_type_spinner)
    lateinit var mSpinnerSplitType : Spinner
    lateinit var mSplitTypeSpinnerAdapter: ArrayAdapter<String>

    @BindView(R.id.transaction_equal_split)
    lateinit var mContainerEqualSplit : LinearLayout

    @BindView(R.id.equal_split_recycle_view)
    lateinit var mEqualSplitRV : RecyclerView
    lateinit var mEqualSplitAdapter: UserCheckmarkAdapter

    @BindView(R.id.amount_split_recycler_view)
    lateinit var mAmountSplitRV : RecyclerView
    lateinit var mAmountSplitAdapter: UserEditMoneyAdapter

    @BindView(R.id.percent_split_recycle_view)
    lateinit var mPercentSplitRV: RecyclerView
    lateinit var mPercentSplitAdapter: UserPercentageAdapter

    @BindView(R.id.transaction_amount_split)
    lateinit var mContainerAmountSplit : LinearLayout

    @BindView(R.id.transaction_percent_split)
    lateinit var mContainerPercentSplit : LinearLayout

    @OnItemSelected(R.id.split_type_spinner)
    fun onSplitTypeChanged() {
        mContainerAmountSplit.visibility = View.GONE
        mContainerEqualSplit.visibility = View.GONE
        mContainerPercentSplit.visibility = View.GONE

        val selection = mSpinnerSplitType.selectedItem.toString()

        if (selection == "Equally") {
            Log.d("SPLIT CHANGED", "Equally == $selection")
            mSplitType = Constants.SPLIT_EQUALLY
            mContainerAmountSplit.visibility = View.VISIBLE
        } else if (selection == "By Amount") {
            Log.d("SPLIT CHANGED", "By Amount == $selection")
            mSplitType = Constants.SPLIT_BY_AMOUNT
            mContainerEqualSplit.visibility = View.VISIBLE
        } else if (selection == "By Percent") {
            Log.d("SPLIT CHANGED", "By Percent == $selection")
            mSplitType = Constants.SPLIT_BY_PERCENTAGE
            mContainerPercentSplit.visibility = View.VISIBLE
        }

        // TODO: Figure why the fuck these are inverted...
    }

    @OnItemSelected(R.id.paid_by_spinner)
    fun paidByChanged() {
        val userId: Long = mUsers!![mSpinnerPaidBy.selectedItemPosition].externalId
        mPaySplit = mutableMapOf(mUsers!![mSpinnerPaidBy.selectedItemPosition].externalId to mAmount)
    }

    @OnClick(R.id.save_button)
    fun saveTransaction() {
        try {
            mPaySplit = buildPaySplit()
            mOweSplit = buildOweSplit()

            if (formIsValid()) {

                val intent: Intent = Intent()

                if (mSplitType == Constants.SPLIT_BY_PERCENTAGE) {
                    mPercentSplit = mPercentSplitAdapter.getPercentSplit()
                    intent.putExtra("oweSplit", gson.toJson(mPercentSplit))
                } else {
                    intent.putExtra("oweSplit", gson.toJson(mOweSplit))
                }

                intent.putExtra("paySplit", gson.toJson(mPaySplit))
                intent.putExtra("splitType", mSplitType)
                intent.putExtra("amount", mAmount)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } catch (e: Exception) {
            Log.e("EDIT_TRANSACTION_SPLIT", "something went HORRIBLY wrong while saving", e)
        }
    }

    fun formIsValid() : Boolean {
        // TODO: Form Validation
        return true
    }

    fun buildPaySplit() : MutableMap<Long, Long> {
        return mutableMapOf(mUsers!![mSpinnerPaidBy.selectedItemPosition].externalId to mAmount)
    }

    fun buildOweSplit(): MutableMap<Long, Long> {
        var oweSplit : MutableMap<Long, Long> = mutableMapOf()

        when (mSplitType) {
            Constants.SPLIT_EQUALLY -> {
                oweSplit = SplitUtil.equalSplit(mAmount, mEqualSplitAdapter.getUserIds())
            }
            Constants.SPLIT_BY_AMOUNT -> {
                oweSplit = mAmountSplitAdapter.getOweSplit()
            }
        }

        return oweSplit
    }
}

fun Context.CreateEditTransactionIntent(): Intent {
    return Intent(this, EditTransactionSplitActivity::class.java)
}