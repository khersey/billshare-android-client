package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import butterknife.*
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.SplitUtil
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.CreateTransactionPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateTransactionView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_transaction.*
import java.io.Serializable
import javax.inject.Inject

class CreateTransactionActivity : BaseActivity(), CreateTransactionView {


    private val VERTICAL_SPACING: Int = 30

    @BindView(R.id.label_edit_text)
    lateinit var mLabelEditText: EditText

    @BindView(R.id.select_group_spinner)
    lateinit var mGroupSpinner: Spinner

    @BindView(R.id.amount_edit_text)
    lateinit var mAmountEditText: EditText
    @OnTextChanged(R.id.amount_edit_text)
    fun amountChanged() {
        try {
            mAmount = (mAmountEditText.text.toString().toDouble() * 100).toLong()
            oweSplit = SplitUtil.equalSplit(mAmount, mSelectedGroup!!.users!!.map { user -> user.externalId })
            paySplit = mutableMapOf(mCurrentUser!!.externalId to mAmount)
        } catch(exception: Exception) {

        }
    }

    @BindView(R.id.select_currency_code_spinner)
    lateinit var mCurrencyCodeSpinner: Spinner

    @BindView(R.id.activity_create_transaction)
    lateinit var mView: View

    @BindView(R.id.paid_by_text)
    lateinit var mPaidByText: TextView

    @BindView(R.id.split_between_text)
    lateinit var mSplitBetweenText: TextView

    val gson: Gson = Gson()

    @OnClick(R.id.edit_transaction_btn)
    fun editTransactionClicked() {
        var intent = this.CreateEditTransactionIntent()
        intent.putExtra("amount", mAmount)
        val list = mSelectedGroup!!.users
        intent.putExtra("users", gson.toJson(list))
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            var temp: MutableMap<Long, Long> = (data!!.getSerializableExtra("oweSplit") as MutableMap<Long, Long>)
            if (temp != null) {
                oweSplit = temp
            }
            temp = (data!!.getSerializableExtra("paySplit") as MutableMap<Long, Long>)
            if (temp != null) {
                paySplit = temp
            }
        }
    }

    @Inject
    lateinit var mPresenter: CreateTransactionPresenter

    lateinit var mGroupSpinnerAdapter: ArrayAdapter<String>

    var mGroups : MutableList<Group> = mutableListOf()
    var mSelectedGroup: Group? = null
    var mCurrentUser: User? = null
    var mTransactionType: Int = Constants.SPLIT_EQUALLY
    var oweSplit: MutableMap<Long, Long> = mutableMapOf()
    var paySplit: MutableMap<Long, Long> = mutableMapOf()
    var mAmount: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        mCurrentUser = User(1, null, null, null, null)

        mPresenter.getGroupsByUserId(mCurrentUser!!.externalId) // TODO: replace with sharedPreference
        mPresenter.getUser(mCurrentUser!!.externalId)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupSpinners()
        setupTextViews()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getGroupsByUserId(mCurrentUser!!.externalId)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

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

    @OnItemSelected(R.id.select_group_spinner)
    fun onGroupSelected(pos: Int) {
        val label = mGroupSpinner.getItemAtPosition(pos)
        mSelectedGroup = mGroups.find { group -> group.label == label }

        var paySplitString : String = ""
        paySplitString += "${mCurrentUser?.fName} ${mCurrentUser?.lName!![0]}"
        mPaidByText.text = paySplitString

        var oweSplitString : String = ""
        mSelectedGroup?.users?.forEach { user -> oweSplitString += "${user.fName} ${user.lName!![0]}.\n" }
        mSplitBetweenText.text = oweSplitString

    }

    @OnClick(R.id.save_button)
    fun saveTransaction() {
        if (formIsValid()) {
            val currencyCode: String = mCurrencyCodeSpinner.selectedItem.toString()
            val label : String = mLabelEditText.text.toString()
            val newTransaction = Transaction(0, mSelectedGroup!!, label, mAmount,currencyCode, false, paySplit, oweSplit)
            mPresenter.createTransaction(newTransaction)
        }
    }

    override fun transactionCreated(transactionReceiver: TransactionReceiver) {
        val transaction: Transaction = transactionReceiver.toTransaction()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    fun formIsValid() : Boolean {
        // TODO: Form Validation
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

    override fun showProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }
}

fun Context.CreateTransactionIntent(): Intent {
    return Intent(this, CreateTransactionActivity::class.java)
}