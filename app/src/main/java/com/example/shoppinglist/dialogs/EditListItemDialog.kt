package com.example.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.EditListDialogBinding
import com.example.shoppinglist.databinding.NewListDialogBinding
import com.example.shoppinglist.entities.ShopListItem

object EditListItemDialog {

    fun showDialog(context: Context, listener: Listener, item:ShopListItem) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.edName.setText(item.name)
        binding.edInfo.setText(item.item_info)
        if (item.item_type == 1)
            binding.edInfo.visibility = View.GONE
        binding.btnUppdate.setOnClickListener {
            if (binding.edName.text.isNotEmpty()) {
                val itemInfo = if (binding.edInfo.text.toString().isEmpty()) null else binding.edInfo.text.toString()
                listener.onClick(item.copy(name = binding.edName.text.toString(), item_info = itemInfo))
            }
            dialog?.dismiss()
        }


        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }

}