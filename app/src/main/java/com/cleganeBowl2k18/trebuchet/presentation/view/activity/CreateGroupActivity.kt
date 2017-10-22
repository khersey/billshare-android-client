package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity

class CreateGroupActivity : BaseActivity() {

    private val VERTICAL_SPACING: Int = 30

    @BindView(R.id.create_group_recycle_view)
    lateinit var mUserListRV: RecyclerView

    @BindView(R.id.group_label_text_input)
    lateinit var mGroupLabelInput: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
    }


}
