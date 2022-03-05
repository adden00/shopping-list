package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "shopping_list_item")
data class ShoppingListItem(

    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "item_info")
    val item_info: String?,

    @ColumnInfo (name = "checked")
    val checked: Int = 0,

    @ColumnInfo (name = "lest_id") // id списка, в котором этот элемент лежит
    val elements_ids: Int,

    @ColumnInfo (name = "item_type")
    val item_type: String
)
