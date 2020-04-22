package com.rosseti.iddog.dialog

import android.app.AlertDialog
import android.content.Context
import com.rosseti.iddog.R

object ErrorDialog {
    private lateinit var errorDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    fun show(context: Context, message: String) {
        if(!::builder.isInitialized) {
            builder = AlertDialog.Builder(context)
            builder.setNegativeButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            errorDialog = builder.create()
        }
        errorDialog.setMessage(message)
        errorDialog.show()
    }
}

