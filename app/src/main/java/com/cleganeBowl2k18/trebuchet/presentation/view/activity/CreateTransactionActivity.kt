package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnItemSelected
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.CreateTransactionPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateTransactionView
import kotlinx.android.synthetic.main.activity_create_transaction.*
import javax.inject.Inject

class CreateTransactionActivity : BaseActivity(), CreateTransactionView {


    private val VERTICAL_SPACING: Int = 30

    @BindView(R.id.label_edit_text)
    lateinit var mLabelEditText: TextInputEditText

    @BindView(R.id.select_group_spinner)
    lateinit var mGroupSpinner: Spinner

    @BindView(R.id.amount_edit_text)
    lateinit var mAmountEditText: EditText

    @BindView(R.id.select_currency_code_spinner)
    lateinit var mCurrencyCodeSpinner: Spinner

    @Inject
    lateinit var mPresenter: CreateTransactionPresenter

    lateinit var mGroupSpinnerAdapter: ArrayAdapter<String>

    var mGroups : MutableList<Group> = mutableListOf()
    var mSelectedGroup: Group? = null
    var mCurrentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupSpinners()
    }

    private fun setupSpinners() {
        mGroupSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mGroups.map { group: Group -> group.label!! })
        mGroupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mGroupSpinner.adapter = mGroupSpinnerAdapter
    }

    @OnItemSelected(R.id.select_group_spinner)
    fun onGroupSelected(pos: Int) {
        val label = mGroupSpinner.getItemAtPosition(pos)
        mSelectedGroup = mGroups.find { group -> group.label == label }
    }

    @OnClick(R.id.save_button)
    fun saveTransaction() {
        if (formIsValid()) {
            val currencyCode: String = mCurrencyCodeSpinner.selectedItem.toString()
            // CreateTransaction()
            // presenter.useCase()
            finish()
        }
    }

    fun formIsValid() : Boolean {
        // TODO: Form Validation
        return false
    }

    override fun userFetched(user: User) {
        mCurrentUser = user
    }

    override fun showError(error: String) {
        // snackbar in an error here
    }

    override fun groupsFetched(groups: List<Group>) {
        mGroups = (groups as MutableList<Group>)
        mGroupSpinnerAdapter.clear()
        mGroupSpinnerAdapter.addAll(mGroups.map { group -> group.label!! })
    }

    override fun showProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }
}
