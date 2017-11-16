package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.TransactionItemsAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.TransactionDetailPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionDetailsView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject



class TransactionDetailsActivity : BaseActivity(),
        TransactionDetailsView, TransactionItemsAdapter.OnTransactionItemClickListener {

    private var mTransaction: Transaction = Transaction()
    private var mGroup: Group = Group()
    private var gson: Gson = Gson()
    private var prefs: SharedPreferences? = null
    private var mCurrentUserId: Long = 0
    private var mCurrentUser: User = User(mCurrentUserId, null, null, null, null)

    @Inject
    lateinit var mPresenter: TransactionDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        mCurrentUserId = prefs!!.getLong(Constants.CURRENT_USER_ID, -1)

        ButterKnife.bind(this)
        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        unpackIntent()
        setupTextViews()

        // API calls must go last
        mPresenter.setView(this)
        mPresenter.getGroup(mTransaction.group!!.externalId)
        mPresenter.getUser(mCurrentUserId)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() === 0) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("updatedTransaction", gson.toJson(mTransaction))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun unpackIntent() {
        mTransaction = gson.fromJson(intent.getStringExtra("transaction"), object : TypeToken<Transaction>() {}.type)

    }

    private fun setupTextViews() {
        mTransactionLabel.text = mTransaction.label
        val amountAsMoney = mTransaction.amount.toDouble() * 0.01
        mTransactionAmount.text = "$${amountAsMoney} "
        mCurrencyCode.text = mTransaction.currencyCode
        mGroupLabel.text = "in ..."
    }

    private fun setupRecyclerView() {
        mTransactionItemsAdapter = TransactionItemsAdapter(mTransaction, mGroup, mCurrentUserId, this)
        mTransactionItemsAdapter.setPresener(mPresenter)

        mTransactionItemsRV.itemAnimator = DefaultItemAnimator()
        mTransactionItemsRV.addItemDecoration(VerticalSpacingItemDecoration(30))
        mTransactionItemsRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mTransactionItemsRV.setHasFixedSize(true)
        mTransactionItemsRV.adapter = mTransactionItemsAdapter
    }

    @BindView(R.id.transaction_label)
    lateinit var mTransactionLabel: TextView

    @BindView(R.id.transaction_amount)
    lateinit var mTransactionAmount: TextView

    @BindView(R.id.currency_code)
    lateinit var mCurrencyCode: TextView

    @BindView(R.id.group_label)
    lateinit var mGroupLabel: TextView

    @BindView(R.id.transaction_items_rv)
    lateinit var mTransactionItemsRV: RecyclerView
    lateinit var mTransactionItemsAdapter: TransactionItemsAdapter


    override fun userReturned(user: User) {
        mCurrentUser = user
    }

    override fun groupReturned(group: Group) {
        mGroup = group
        mGroupLabel.text = "in ${mGroup.label}"
        mTransaction.group = mGroup

        setupRecyclerView()
    }

    override fun transactionUpdated(transactionReceiver: TransactionReceiver) {
        mTransaction = transactionReceiver.toTransaction()
        mTransaction.group = mGroup
    }

    override fun showError(message: String) {
        // TODO: figure this out
    }

    override fun onTransactionItemClick(lineId: Long) {
        // TODO: probably nothing here
    }

}

fun Context.CreateTransactionDetailItent(): Intent {
    return Intent(this, TransactionDetailsActivity::class.java)
}
