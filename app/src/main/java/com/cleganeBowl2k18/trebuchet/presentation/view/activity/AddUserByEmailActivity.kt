package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.Button
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent

import kotlinx.android.synthetic.main.activity_add_user_by_email.*

class AddUserByEmailActivity : BaseActivity() {

    @BindView(R.id.email_edit_text)
    lateinit var mEmailTextEdit : TextInputEditText

    @BindView(R.id.save_button)
    lateinit var mSaveButton : Button

    @BindView(R.id.toolbar)
    lateinit var mToolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_by_email)
        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)
        setSupportActionBar(toolbar)

        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            fun onClick(view: View) {
                this.finish()
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.save_button)
    fun onSaveClicked() {
        // TODO: add form validation
        val email : String? = mEmailTextEdit.text.toString()

        if (email!!.contains("@")) {
            val intent : Intent = Intent()

            intent.putExtra("email", email)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}

fun Context.CreateAddUserByEmailIntent(): Intent {
    return Intent(this, AddUserByEmailActivity::class.java)
}
