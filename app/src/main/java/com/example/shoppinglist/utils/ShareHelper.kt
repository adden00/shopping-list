package com.example.shoppinglist.utils

import android.content.Intent
import com.example.shoppinglist.entities.ShopListItem
import java.lang.StringBuilder

object ShareHelper {

    fun shareShopList(shopList: List<ShopListItem>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.putExtra(Intent.EXTRA_TEXT, makeText(shopList, listName))
        return intent

    }

    private fun makeText(shopList: List<ShopListItem>, listName: String): String {
        val sBuilder = StringBuilder()
        sBuilder.append("<<$listName>>\n")
        var count = 0
        shopList.forEach {
            sBuilder.append("${++count} - ${it.name} (${it.item_info})\n")

        }
        return sBuilder.toString()
    }
}