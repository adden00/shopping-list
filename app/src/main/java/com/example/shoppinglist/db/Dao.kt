package com.example.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query ("SELECT * FROM NOTE_LIST")   // ссчитывание
    fun getAllNotes(): Flow<List<NoteItem>>     // при изменении в базе получаем поток корутины постоянно, после запуска функции


    @Query ("DELETE FROM NOTE_LIST WHERE id IS :id")   // удаление по id
    suspend fun deleteNote(id: Int)    // запускаем на корутине удаление

    @Insert  // запись
    suspend fun insertNote(note: NoteItem)     // чтобы запускать на корутине

    @Insert
    suspend fun insertListName(name: ShoppingListName) // добавить список

    @Query ("SELECT * FROM shopping_list_names")
    fun getAllShoppingLists(): Flow<List<ShoppingListName>>

    @Update  // запись
    suspend fun updateNote(note: NoteItem)

    @Query ("DELETE FROM SHOPPING_LIST_NAMES WHERE id IS :id")   // удаление по id
    suspend fun deleteShopListName(id: Int)    // запускаем на корутине удаление




}