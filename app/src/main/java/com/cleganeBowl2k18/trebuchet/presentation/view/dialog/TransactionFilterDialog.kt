package com.cleganeBowl2k18.trebuchet.presentation.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by khersey on 2017-11-29.
 */
class TransactionFilterDialog() : DialogFragment() {

    interface TransactionFilterListener {

        fun onFilterSelected(option: String)

        fun onGroupFilterSelect(groupId: Long)
    }

    lateinit var mListener: TransactionFilterListener

    var mOptions: List<String> = listOf("Newest", "Oldest", "Largest Amount", "Smallest Amount", "I Paid", "I Owe Money")
    // TODO: add by group

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setTitle("Sort or Filter")
                .setItems(mOptions.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                    if (mOptions[which] == "By Group") {
                        // TODO: open a group select dialog
                        mListener.onGroupFilterSelect(-1)
                    } else {
                        mListener.onFilterSelected(mOptions[which])
                    }
                })

        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mListener = activity as TransactionFilterListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement NoticeDialogListener")
        }

    }



}