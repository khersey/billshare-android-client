package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.cleganeBowl2k18.trebuchet.R

import kotlinx.android.synthetic.main.activity_add_user_by_email.*

class AddUserByEmailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_by_email)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
