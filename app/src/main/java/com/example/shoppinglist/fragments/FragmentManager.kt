package com.example.shoppinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R

object FragmentManager {
    var currentFrag: BaseFragment? = null // текущий фрагмент для обращения к нему из любого места активити

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction =
            activity.supportFragmentManager.beginTransaction() // трансакция чтобы создавать, менять, удалять фрагмент
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}