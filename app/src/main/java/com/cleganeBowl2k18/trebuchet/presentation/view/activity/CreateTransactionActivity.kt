package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.widget.EditText
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.CreateGroupPresenter
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
    lateinit var mCurrentCodeSpinner: Spinner

    @Inject
    lateinit var mPresenter: CreateTransactionPresenter


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
    }


    



    override fun showProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
         //To change body of created functions use File | Settings | File Templates.
    }
}
