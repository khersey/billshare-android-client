package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import butterknife.*
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.SplitUtil
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.CreateTransactionPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateTransactionView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_create_transaction.*
import javax.inject.Inject

class CreateTransactionActivity : BaseActivity(), CreateTransactionView {

    // class variables
    private val VERTICAL_SPACING: Int = 30
    @Inject
    lateinit var mPresenter: CreateTransactionPresenter

    lateinit var mGroupSpinnerAdapter: ArrayAdapter<String>

    val gson: Gson = Gson()
    private var prefs: SharedPreferences? = null
    var mGroups : MutableList<Group> = mutableListOf()
    var mSelectedGroup: Group? = null
    var mCurrentUser: User? = null
    var mSplitType: Int = -1
    var mOweSplit: MutableMap<Long, Long> = mutableMapOf()
    var mPaySplit: MutableMap<Long, Long> = mutableMapOf()
    var mAmount: Long = 0
    var mCurrentUserId: Long = 0


    // Lifecycle Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        setSupportActionBar(toolbar)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)

        mPresenter.getUser(mCurrentUserId)
        mPresenter.getGroupsByUserId(mCurrentUserId)

        mSplitType = Constants.SPLIT_EQUALLY

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupSpinners()
        setupTextViews()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            mOweSplit = gson.fromJson(data!!.getStringExtra("oweSplit"),  object : TypeToken<MutableMap<Long, Long>>() {}.type)
            mPaySplit = gson.fromJson(data.getStringExtra("paySplit"),  object : TypeToken<MutableMap<Long, Long>>() {}.type)
            mSplitType = data.getIntExtra("splitType", Constants.SPLIT_EQUALLY)
            mAmount = data.getLongExtra("amount" ,(mAmountEditText.text.toString().toDouble() * 100).toLong())
            mAmountEditText.setText("${mAmount.toDouble() * 0.01}")

            updateTransactionSplitText()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("mGroups", gson.toJson(mGroups))
        outState.putString("mSelecedGroup", gson.toJson(mSelectedGroup))
        outState.putString("mOweSplit", gson.toJson(mOweSplit))
        outState.putString("mPaySplit", gson.toJson(mPaySplit))
        outState.putString("mCurrentUser", gson.toJson(mCurrentUser))
        outState.putInt("mSplitType", mSplitType)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mGroups        = gson.fromJson(savedInstanceState.getString("mGroups"), object : TypeToken<MutableList<Group>>() {}.type)
        mSelectedGroup = gson.fromJson(savedInstanceState.getString("mSelectedGroup"), object : TypeToken<Group>() {}.type)
        mOweSplit      = gson.fromJson(savedInstanceState.getString("mOweSplit"), object : TypeToken<MutableMap<Long, Long>>() {}.type)
        mPaySplit      = gson.fromJson(savedInstanceState.getString("mPaySplit"), object : TypeToken<MutableMap<Long, Long>>() {}.type)
        mCurrentUser   = gson.fromJson(savedInstanceState.getString("mCurrentUser"), object : TypeToken<User>() {}.type)
        mSplitType     = savedInstanceState.getInt("mSplitType")
    }

    // Helper Functions
    private fun setupSpinners() {
        mGroupSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mGroups.map { group: Group -> group.label!! })
        mGroupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mGroupSpinner.adapter = mGroupSpinnerAdapter
    }

    private fun setupTextViews() {
        mPaidByText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        mPaidByText.setSingleLine(false)
        mSplitBetweenText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        mSplitBetweenText.setSingleLine(false)
    }

    // UI Elements
    @BindView(R.id.label_edit_text)
    lateinit var mLabelEditText: EditText

    @BindView(R.id.select_group_spinner)
    lateinit var mGroupSpinner: Spinner

    @BindView(R.id.amount_edit_text)
    lateinit var mAmountEditText: EditText

    @BindView(R.id.select_currency_code_spinner)
    lateinit var mCurrencyCodeSpinner: Spinner

    @BindView(R.id.paid_by_text)
    lateinit var mPaidByText: TextView

    @BindView(R.id.split_between_text)
    lateinit var mSplitBetweenText: TextView

    @OnClick(R.id.edit_transaction_btn)
    fun editTransactionClicked() {
        var intent = this.CreateEditTransactionIntent()
        intent.putExtra("amount", mAmount)
        intent.putExtra("users", gson.toJson(mSelectedGroup!!.users))
        intent.putExtra("splitType", mSplitType)
        intent.putExtra("paySplit", gson.toJson(mPaySplit))
        intent.putExtra("oweSplit", gson.toJson(mOweSplit))
        startActivityForResult(intent, 0)
    }

    @OnItemSelected(R.id.select_group_spinner)
    fun onGroupSelected(pos: Int) {
        val label = mGroupSpinner.getItemAtPosition(pos)
        mSelectedGroup = mGroups.find { group -> group.label == label }
        mSplitType = Constants.SPLIT_EQUALLY
        initializeSplits()
    }

    @OnClick(R.id.save_button)
    fun saveTransaction() {
        if (formIsValid()) {
            val currencyCode: String = mCurrencyCodeSpinner.selectedItem.toString()
            val label : String = mLabelEditText.text.toString()
            val newTransaction = Transaction(0, mSelectedGroup!!, label, mAmount, currencyCode, mCurrentUserId, mPaySplit, mOweSplit)
            newTransaction.splitType = mSplitType
            mPresenter.createTransaction(newTransaction)
        }
    }

    @OnTextChanged(R.id.amount_edit_text)
    fun amountChanged() {

        if (mSplitType == Constants.SPLIT_EQUALLY) {
            try {
                mAmount = (mAmountEditText.text.toString().toDouble() * 100).toLong()
            } catch(exception: Exception) {
                mAmount = 0
            }
            mOweSplit = SplitUtil.equalSplit(mAmount, mOweSplit.keys.toList())
            mPaySplit = SplitUtil.equalSplit(mAmount, mPaySplit.keys.toList())
            updateTransactionSplitText()
        } else if (mSplitType == Constants.SPLIT_BY_AMOUNT) {
            updateTransactionSplitText()
        } else {
            updateTransactionSplitText()
        }

    }

    override fun showProgress() {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        //To change body of created functions use File | Settings | File Templates.
    }

    fun initializeSplits() {
        mOweSplit = mutableMapOf()
        mSelectedGroup!!.users!!.map { user: User -> mOweSplit[user.externalId] = 0 }
        mPaySplit = mutableMapOf()
        mPaySplit[mCurrentUserId] = 0
        amountChanged()
    }

    fun updateTransactionSplitText() {
        var paySplitString : String = ""
        for (userId in mPaySplit.keys) {
            var user: User? = mSelectedGroup!!.users!!.find { user -> user.externalId == userId }
            if (user != null) paySplitString += "${user.fName} ${user.lName!![0]} -> $${(mPaySplit[userId]!! * 0.01).toDouble()}\n"
        }
        mPaidByText.text = paySplitString

        var oweSplitString : String = ""
        for (userId in mOweSplit.keys) {
            var user: User? = mSelectedGroup!!.users!!.find { user -> user.externalId == userId }
            if (mSplitType == Constants.SPLIT_BY_PERCENTAGE) {
                if (user != null) oweSplitString += "${user.fName} ${user.lName!![0]} -> %${mOweSplit[userId]!!}\n"
            } else {
                if (user != null) oweSplitString += "${user.fName} ${user.lName!![0]} -> $${(mOweSplit[userId]!! * 0.01).toDouble()}\n"
            }

        }
        mSplitBetweenText.text = oweSplitString
    }

    // useCase Stuff
    override fun transactionCreated(transactionReceiver: TransactionReceiver) {
        val transaction: Transaction = transactionReceiver.toTransaction()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    fun formIsValid() : Boolean {
        val oweTotal: Long = mOweSplit.values.sum()
        if (mSplitType != Constants.SPLIT_BY_PERCENTAGE) {
            if (oweTotal != mAmount) {
                // TODO: display error
                return false
            }
        } else {
            val hundred: Long = 100
            if (oweTotal != hundred) {
                // TODO: display error
                return false
            }
        }

        val payTotal: Long = mPaySplit.values.sum()
        if (payTotal != mAmount) {
            // TODO: display error
            return false
        }

        return true
    }

    override fun userFetched(user: User) {
        mCurrentUser = user
        // TODO: remove this, we dont need it
    }


    override fun showError(error: String) {
        // snackbar in an error here
    }

    override fun groupsFetched(groups: List<Group>) {
        mGroups = groups.toMutableList()
        mGroupSpinnerAdapter.clear()
        mGroupSpinnerAdapter.addAll(mGroups.map { group -> group.label!! })
        mGroupSpinnerAdapter.notifyDataSetChanged()
        onGroupSelected(0)
    }

}

fun Context.CreateTransactionIntent(): Intent {
    return Intent(this, CreateTransactionActivity::class.java)
}