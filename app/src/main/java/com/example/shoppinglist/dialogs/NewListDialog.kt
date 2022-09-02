package com.example.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.shoppinglist.databinding.NewListDialogBinding

object NewListDialog {

    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.button.setOnClickListener {
            val listName = binding.editTextTextPersonName.text.toString()
            if (listName.isNotEmpty()) {
                listener.onClick(listName)
            }
            dialog?.dismiss()

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }

}