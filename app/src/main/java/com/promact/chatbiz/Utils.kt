package com.promact.chatbiz

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView

class Utils {

    fun createDialog(context: Context, layoutInflater: LayoutInflater,title: String): AlertDialog {

        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog,null)
        val message = dialogView.findViewById<TextView>(R.id.message)
        message.text = title
        builder.setView(dialogView)
        builder.setCancelable(false)

        return builder.create()
    }
}