package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "shopping_list_names") // таблица типов списков
data class ShoppingListName(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "time")
    val time: String,

    @ColumnInfo(name = "all_item_count") // сколько всего элементов в списке
    val all_item_count: Int,

    @ColumnInfo(name = "checked_item_count") // сколько выполненных
    val checked_item_count: Int,

    @ColumnInfo (name = "items_ids")
    val items_ids: String

): Serializable
