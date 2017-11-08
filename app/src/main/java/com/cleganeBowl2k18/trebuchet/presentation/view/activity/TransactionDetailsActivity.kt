package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.TransactionDetailPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionDetailsView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class TransactionDetailsActivity : BaseActivity(), TransactionDetailsView {

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

    @BindView(R.id.transaction_label)
    lateinit var mTransactionLabel: TextView

    @BindView(R.id.transaction_amount)
    lateinit var mTransactionAmount: TextView

    @BindView(R.id.currency_code)
    lateinit var mCurrencyCode: TextView

    @BindView(R.id.group_label)
    lateinit var mGroupLabel: TextView


    override fun userReturned(user: User) {
        mCurrentUser = user
    }

    override fun groupReturned(group: Group) {
        mGroup = group
        mGroupLabel.text = "in ${mGroup.label}"
        mTransaction.group = mGroup
    }

    override fun showError(message: String) {
        // TODO: figure this out
    }

}

fun Context.CreateTransactionDetailItent(): Intent {
    return Intent(this, TransactionDetailsActivity::class.java)
}
