package com.example.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.shoppinglist.databinding.DeleteListDialogBinding
import com.example.shoppinglist.databinding.NewListDialogBinding

object DeleteDialog {

    fun deleteItem(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.btnDelete.setOnClickListener {
            listener.onClick()
            dialog?.dismiss()

        }
        binding.btnCancel.setOnClickListener {

            dialog?.dismiss()

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }

}